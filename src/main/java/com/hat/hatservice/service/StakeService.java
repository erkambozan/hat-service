package com.hat.hatservice.service;

import com.hat.hatservice.api.dto.StakeRequest;
import com.hat.hatservice.api.dto.UserResponse;
import com.hat.hatservice.db.Stake;
import com.hat.hatservice.db.StakeSettings;
import com.hat.hatservice.db.StakeSettingsRepository;
import com.hat.hatservice.db.UserTotalBalance;
import com.hat.hatservice.db.UserTotalBalanceRepository;
import com.hat.hatservice.exception.InsufficientBalance;
import com.hat.hatservice.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

public class StakeService {
	private static final Logger logger = LoggerFactory.getLogger(StakeService.class);

	private final UserTotalBalanceRepository userTotalBalanceRepository;

	private final StakeSettingsRepository stakeSettingsRepository;

	private final UserService userService;

	public StakeService(UserTotalBalanceRepository userTotalBalanceRepository, StakeSettingsRepository stakeSettingsRepository, UserService userService) {
		this.userTotalBalanceRepository = userTotalBalanceRepository;
		this.stakeSettingsRepository = stakeSettingsRepository;
		this.userService = userService;
	}

	public void stake(StakeRequest stakeRequest) throws NotFoundException, InsufficientBalance {
		final UserResponse userLoggedDetails =  userService.getLoggedUserDetails();
		logger.info("Stake Silver started with : " + userLoggedDetails.getId());
		Optional<UserTotalBalance> userTotalBalanceOptional = userTotalBalanceRepository.findById(userLoggedDetails.getId());
		UserTotalBalance userTotal = userTotalBalanceOptional.orElseThrow(NotFoundException::new);
		if (userTotal.getLockedBalance() < stakeRequest.getStakeAmount()){
			throw new InsufficientBalance("Insufficient Balance");
		}

		Optional<StakeSettings> stakeSettingsOptional = stakeSettingsRepository.findStakeSettingsByStakeType(stakeRequest.getStakeType());
		StakeSettings stakeSettings = stakeSettingsOptional.orElseThrow(NotFoundException::new);
		if(stakeRequest.getStakeAmount() < stakeSettings.getMinimumLimit()){
			throw new InsufficientBalance("Insufficient Limit");
		}

		Double expiryStakeAmount = stakeRequest.getStakeAmount() + (stakeRequest.getStakeAmount() * stakeSettings.getStakePercentage() / 100);
		Date endDate = java.sql.Date.valueOf(LocalDate.now().plusMonths(stakeSettings.getExpiryStakeTime()));
		new Stake(userLoggedDetails.getId(), stakeRequest.getStakeAmount(), expiryStakeAmount, stakeSettings.getExpiryStakeTime(), stakeSettings.getStakePercentage(), endDate);
		logger.info("Stake amount deleting from User balance : " + stakeRequest.getStakeAmount());
		doStake(stakeRequest.getStakeAmount(), userTotal);
	}

	public void withdrawMoney(Double amount, UserTotalBalance userTotalBalance){
		userTotalBalance.setTotalBalance(userTotalBalance.getTotalBalance() - amount);
		userTotalBalance.setWithdrawableBalance(userTotalBalance.getWithdrawableBalance() - amount);
	}

	public void stakeProfit(Double stakeAmount, Double stakeProfitAmount, UserTotalBalance userTotalBalance){
		userTotalBalance.setTotalBalance(userTotalBalance.getTotalBalance() + stakeAmount + stakeProfitAmount);
		userTotalBalance.setLockedBalance(userTotalBalance.getLockedBalance() + stakeAmount);
		userTotalBalance.setWithdrawableBalance(userTotalBalance.getWithdrawableBalance() + stakeProfitAmount);
	}

	public void referenceProfit(Double amount, UserTotalBalance userTotalBalance){
		userTotalBalance.setTotalBalance(userTotalBalance.getTotalBalance() + amount);
		userTotalBalance.setWithdrawableBalance(userTotalBalance.getWithdrawableBalance() + amount);
	}

	public void doStake(Double amount, UserTotalBalance userTotalBalance){
		userTotalBalance.setTotalBalance(userTotalBalance.getTotalBalance() - amount);
		userTotalBalance.setLockedBalance(userTotalBalance.getLockedBalance() - amount);
	}

	// TODO Locked amount when will leave from locked amount;
}
