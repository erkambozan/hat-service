package com.hat.hatservice.service;

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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentService {

	private final PaymentRepository paymentRepository;
	private final UserRepository userRepository;
	private final UserTotalBalanceRepository userTotalBalanceRepository;
	private final UserService userService;
	private final Double tokenPrice = 0.002;

	public PaymentService(PaymentRepository paymentRepository, UserRepository userRepository, UserTotalBalanceRepository userTotalBalanceRepository, UserService userService) {
		this.paymentRepository = paymentRepository;
		this.userRepository = userRepository;
		this.userTotalBalanceRepository = userTotalBalanceRepository;
		this.userService = userService;
	}

	@Scheduled(cron = "0 20 * * * ?")
	public void getAllPayments() throws IOException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url("https://api.commerce.coinbase.com/charges")
				.get()
				.addHeader("Accept", "application/json")
				.addHeader("X-CC-Version", "2021-01-01")
				.addHeader("X-CC-Api-Key", "bea1f8df-c862-4a63-9196-75dedd845164")
				.build();

		Response response = client.newCall(request).execute();

		ObjectMapper om = new ObjectMapper();
		Root root = om.readValue(response.body().string(), Root.class);

		List<com.hat.hatservice.api.dto.payments.Payment> payments = new ArrayList<>();
		root.getData().forEach(data -> payments.addAll(data.getPayments()));

		payments.stream().filter(payment -> payment.getDeposited().getStatus().equals("COMPLETED")).forEach(payment -> {
			try {
				OptionalConsumer.of(paymentRepository.findByTransactionId(payment.getTransaction_id())).ifPresentReturnException(new PaymentAlreadyExistException("Payment Already Exist"));
				//TODO Where are coming email, we will find #getDestination will fix
				User user = OptionalConsumer.of(userRepository.findUserByEmail(payment.getDeposited().getDestination())).ifPresent(new NotFoundException("User Not Found"));
				UserTotalBalance userTotalBalance = OptionalConsumer.of(userTotalBalanceRepository.findByUserId(user.getId())).ifPresent(new NotFoundException("User Total Balance Not Found"));

				Double usdAmount = Double.parseDouble(payment.getValue().getLocal().getAmount());
				String currencyName = payment.getDeposited().getAmount().getNet().getCrypto().getCurrency();
				Double currencyAmount = Double.parseDouble(payment.getDeposited().getAmount().getNet().getCrypto().getAmount());
				Double tokenAmount = usdAmount / tokenPrice;

				if (user.getReferenceId() != null) {
					userService.referenceProfit(user.getReferenceId(), tokenAmount);
				}

				//TODO Where are coming email, we will find #getDestination will fix
				userTotalBalance.setWithdrawableBalance(userTotalBalance.getWithdrawableBalance() + tokenAmount);
				userTotalBalanceRepository.save(userTotalBalance);
				userService.entryTransactionsAmount(user.getId(), tokenAmount, "Deposit");
				paymentRepository.save(new Payment(user.getId(), payment.getDeposited().getDestination(), payment.getTransaction_id(), usdAmount, tokenAmount, currencyName, currencyAmount));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
