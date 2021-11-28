package com.hat.hatservice.service;

import com.hat.hatservice.api.dto.StakeRequest;
import com.hat.hatservice.api.dto.StakeSettingsRequest;
import com.hat.hatservice.api.dto.StakeSettingsResponse;
import com.hat.hatservice.api.dto.UserResponse;
import com.hat.hatservice.db.Stake;
import com.hat.hatservice.db.StakeRepository;
import com.hat.hatservice.db.StakeSettings;
import com.hat.hatservice.db.StakeSettingsRepository;
import com.hat.hatservice.db.UserTotalBalance;
import com.hat.hatservice.db.UserTotalBalanceRepository;
import com.hat.hatservice.exception.DuplicateException;
import com.hat.hatservice.exception.InsufficientBalance;
import com.hat.hatservice.exception.NotFoundException;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class StakeService {
	private static final Logger logger = LoggerFactory.getLogger(StakeService.class);

	private final UserTotalBalanceRepository userTotalBalanceRepository;

	private final StakeSettingsRepository stakeSettingsRepository;

	private final StakeRepository stakeRepository;

	private final UserService userService;

	private final Mapper mapper;

	public StakeService(UserTotalBalanceRepository userTotalBalanceRepository, StakeSettingsRepository stakeSettingsRepository, StakeRepository stakeRepository, UserService userService, Mapper mapper) {
		this.userTotalBalanceRepository = userTotalBalanceRepository;
		this.stakeSettingsRepository = stakeSettingsRepository;
		this.stakeRepository = stakeRepository;
		this.userService = userService;
		this.mapper = mapper;
	}

	public void stake(StakeRequest stakeRequest) throws NotFoundException, InsufficientBalance {
		final UserResponse userLoggedDetails =  userService.getLoggedUserDetails();
		logger.info("Stake started with : " + userLoggedDetails.getId());
		Optional<UserTotalBalance> userTotalBalanceOptional = getUserTotalBalance(stakeRequest, userLoggedDetails);
		StakeSettings stakeSettings = getStakeSettings(stakeRequest);
		stakeMaker(stakeRequest, userLoggedDetails, userTotalBalanceOptional, stakeSettings);
	}

	private void stakeMaker(StakeRequest stakeRequest, UserResponse userLoggedDetails, Optional<UserTotalBalance> userTotalBalanceOptional, StakeSettings stakeSettings) {
		Double expiryStakeAmount = stakeRequest.getStakeAmount() + (stakeRequest.getStakeAmount() * stakeSettings.getStakePercentage() / 100);
		Date endDate = java.sql.Date.valueOf(LocalDate.now().plusMonths(stakeSettings.getExpiryStakeTime()));
		stakeRepository.save(new Stake(userLoggedDetails.getId(), stakeRequest.getStakeAmount(), expiryStakeAmount, stakeSettings.getExpiryStakeTime(), stakeSettings.getStakePercentage(), endDate));
		logger.info("Stake amount deleting from User balance : " + stakeRequest.getStakeAmount());
		doStake(stakeRequest.getStakeAmount(), userTotalBalanceOptional.get());
	}

	private Optional<UserTotalBalance> getUserTotalBalance(StakeRequest stakeRequest, UserResponse userLoggedDetails) throws NotFoundException, InsufficientBalance {
		Optional<UserTotalBalance> userTotalBalanceOptional = userTotalBalanceRepository.findByUserId(userLoggedDetails.getId());
		if(userTotalBalanceOptional.isEmpty())
			throw new NotFoundException("User Total Balance Not Found");
		if (userTotalBalanceOptional.get().getLockedBalance() < stakeRequest.getStakeAmount())
			throw new InsufficientBalance("Insufficient Balance");
		return userTotalBalanceOptional;
	}

	private StakeSettings getStakeSettings(StakeRequest stakeRequest) throws NotFoundException, InsufficientBalance {
		Optional<StakeSettings> stakeSettingsOptional = stakeSettingsRepository.findStakeSettingsByStakeType(stakeRequest.getStakeType());
		if(stakeSettingsOptional.isEmpty())
			throw new NotFoundException("Stake Type Not Found");
		StakeSettings stakeSettings = stakeSettingsOptional.get();
		if(stakeRequest.getStakeAmount() < stakeSettings.getMinimumLimit())
			throw new InsufficientBalance("Insufficient Limit");
		return stakeSettings;
	}

	public StakeSettingsResponse createStakeSettings(StakeSettingsRequest request) throws DuplicateException {
		logger.info("Stake Settings Creating : " + request.getStakeType());
		stakeSettingsRepository.findStakeSettingsByStakeType(request.getStakeType()).ifPresent(x -> {
			try {
				throw new DuplicateException("With this stake settings type already exist");
			} catch (DuplicateException e) {
				e.printStackTrace();
			}
		});
		StakeSettings stakeSettings = stakeSettingsRepository.save(new StakeSettings(request.getExpiryStakeTime(), request.getStakePercentage(), request.getStakeType(), request.getMinimumLimit()));
		return new StakeSettingsResponse(stakeSettings.getId(), stakeSettings.getExpiryStakeTime(), stakeSettings.getStakePercentage(), stakeSettings.getStakeType(), stakeSettings.getMinimumLimit());
	}

	public void updateStakeSettings(StakeSettingsRequest request, UUID id) throws NotFoundException {
		logger.info("Updating Stake Settings : " + id);
		StakeSettings stakeSettings = stakeSettingsRepository.findById(id).orElseThrow(NotFoundException::new);
		mapper.map(request, stakeSettings);
		stakeSettingsRepository.save(stakeSettings);
	}

	public void withdrawMoney(Double amount, UserTotalBalance userTotalBalance){
		userTotalBalance.setTotalBalance(userTotalBalance.getTotalBalance() - amount);
		userTotalBalance.setWithdrawableBalance(userTotalBalance.getWithdrawableBalance() - amount);
		userTotalBalanceRepository.save(userTotalBalance);
	}

	public void stakeProfit(Double stakeAmount, Double stakeProfitAmount, UserTotalBalance userTotalBalance){
		userTotalBalance.setTotalBalance(userTotalBalance.getTotalBalance() + stakeAmount + stakeProfitAmount);
		userTotalBalance.setLockedBalance(userTotalBalance.getLockedBalance() + stakeAmount);
		userTotalBalance.setWithdrawableBalance(userTotalBalance.getWithdrawableBalance() + stakeProfitAmount);
		userTotalBalanceRepository.save(userTotalBalance);
	}

	public void referenceProfit(Double amount, UserTotalBalance userTotalBalance){
		userTotalBalance.setTotalBalance(userTotalBalance.getTotalBalance() + amount);
		userTotalBalance.setWithdrawableBalance(userTotalBalance.getWithdrawableBalance() + amount);
		userTotalBalanceRepository.save(userTotalBalance);
	}

	public void doStake(Double amount, UserTotalBalance userTotalBalance){
		userTotalBalance.setTotalBalance(userTotalBalance.getTotalBalance() - amount);
		userTotalBalance.setLockedBalance(userTotalBalance.getLockedBalance() - amount);
		userTotalBalanceRepository.save(userTotalBalance);
	}

	// TODO Locked amount when will leave from locked amount;
}
