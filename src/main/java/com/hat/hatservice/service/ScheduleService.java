package com.hat.hatservice.service;

import com.hat.hatservice.db.Stake;
import com.hat.hatservice.db.StakeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

public class ScheduleService {
	private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

	StakeRepository stakeRepository;

	public ScheduleService(StakeRepository stakeRepository) {
		this.stakeRepository = stakeRepository;
	}

	@Scheduled(cron = "0/20 * * * * ?")
	public void publish() {
		Date currentUtilDate = new Date();
		List<Stake> stakes = stakeRepository.findAllByEndDate(currentUtilDate);

		stakes.forEach(System.out::println);

	}
}
