package com.hat.hatservice.schedule;

import com.hat.hatservice.db.Stake;
import com.hat.hatservice.db.StakeRepository;
import com.hat.hatservice.db.UserTotalBalance;
import com.hat.hatservice.db.UserTotalBalanceRepository;
import com.hat.hatservice.exception.NotFoundException;
import com.hat.hatservice.service.StakeService;
import com.hat.hatservice.utils.OptionalConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Component
public class ScheduleService {
	private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

	private final StakeService stakeService;

	private final StakeRepository stakeRepository;

	private final UserTotalBalanceRepository userTotalBalanceRepository;

	public ScheduleService(StakeService stakeService, StakeRepository stakeRepository, UserTotalBalanceRepository userTotalBalanceRepository) {
		this.stakeService = stakeService;
		this.stakeRepository = stakeRepository;
		this.userTotalBalanceRepository = userTotalBalanceRepository;
	}

	@Scheduled(cron = "0 12 * * * ?")
	public void publish() throws ParseException{
		logger.info("Scheduled method worked");
		DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Date currentDate = new SimpleDateFormat("dd/MM/yyyy").parse(customFormatter.format(LocalDate.now()));
		List<Stake> stakes = stakeRepository.findAllByEndDateAndStakeStatus(currentDate, true);
		stakes.forEach(stake -> {
			UserTotalBalance userTotalBalance = new UserTotalBalance();
			try {
				userTotalBalance = OptionalConsumer.of(userTotalBalanceRepository.findByUserId(stake.getUserId())).ifPresent(new NotFoundException("User balance not found"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Stake completed user id : " + stake.getUserId());
			stakeService.stakeProfit(stake.getExpiryStakeAmount(), userTotalBalance);
			try {
				stakeService.finishStake(stake.getId(), stakeRepository);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}
}
