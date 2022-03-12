package com.hat.hatservice.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hat.hatservice.api.dto.payments.Root;
import com.hat.hatservice.db.Payment;
import com.hat.hatservice.db.PaymentRepository;
import com.hat.hatservice.db.User;
import com.hat.hatservice.db.UserRepository;
import com.hat.hatservice.db.UserTotalBalance;
import com.hat.hatservice.db.UserTotalBalanceRepository;
import com.hat.hatservice.exception.NotFoundException;
import com.hat.hatservice.exception.PaymentAlreadyExistException;
import com.hat.hatservice.utils.OptionalConsumer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.UUID;

@Component
public class PaymentService {

	private final PaymentRepository paymentRepository;
	private final UserRepository userRepository;
	private final UserTotalBalanceRepository userTotalBalanceRepository;
	private final UserService userService;
	@Value("${config.coinPrice}")
	private Double coinPrice;

	public PaymentService(PaymentRepository paymentRepository, UserRepository userRepository, UserTotalBalanceRepository userTotalBalanceRepository, UserService userService) {
		this.paymentRepository = paymentRepository;
		this.userRepository = userRepository;
		this.userTotalBalanceRepository = userTotalBalanceRepository;
		this.userService = userService;
	}

	@Scheduled(cron = "5 * * * * ?")
	public void getAllPayments() throws IOException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url("https://api.commerce.coinbase.com/charges")
				.get()
				.addHeader("Accept", "application/json")
				.addHeader("X-CC-Version", "")
				.addHeader("X-CC-Api-Key", "a635258f-2fcb-42a0-a43b-26ba4448e499")
				.build();

		Response response = client.newCall(request).execute();

		ObjectMapper om = new ObjectMapper();
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Root root = om.readValue(response.body().string(), Root.class);

		root.getData().forEach(data -> data.getPayments().stream().filter(payment -> payment.getDeposited().getStatus().equals("COMPLETED")).forEach(payment -> {
			try {
				OptionalConsumer.of(paymentRepository.findByTransactionId(payment.getTransaction_id())).ifPresentReturnException(new PaymentAlreadyExistException("Payment Already Exist"));
				User user = OptionalConsumer.of(userRepository.findUserByEmail(data.getMetadata().getEmail())).ifPresent(new NotFoundException("User Not Found"));
				UserTotalBalance userTotalBalance = OptionalConsumer.of(userTotalBalanceRepository.findByUserId(user.getId())).ifPresent(new NotFoundException("User Total Balance Not Found"));

				Double usdAmount = Double.parseDouble(payment.getValue().getLocal().getAmount());
				String currencyName = payment.getDeposited().getAmount().getNet().getCrypto().getCurrency();
				Double currencyAmount = Double.parseDouble(payment.getDeposited().getAmount().getNet().getCrypto().getAmount());
				Double tokenAmount = usdAmount / coinPrice;

				if (user.getReferenceId() != null) {
					userService.referenceProfit(user.getReferenceId(), tokenAmount);
				}

				userTotalBalance.setWithdrawableBalance(userTotalBalance.getWithdrawableBalance() + tokenAmount);
				userTotalBalanceRepository.save(userTotalBalance);
				userService.entryTransactionsAmount(user.getId(), UUID.fromString(payment.getTransaction_id()), tokenAmount, "Deposit");
				paymentRepository.save(new Payment(user.getId(), data.getMetadata().getEmail(), payment.getTransaction_id(), usdAmount, tokenAmount, currencyName, currencyAmount));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}));
	}
}
