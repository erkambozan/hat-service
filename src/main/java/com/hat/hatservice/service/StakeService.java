package com.hat.hatservice.service;

import com.hat.hatservice.api.dto.StakeRequest;
import com.hat.hatservice.api.dto.UserResponse;
import com.hat.hatservice.db.StakeSettings;
import com.hat.hatservice.db.StakeSettingsRepository;
import com.hat.hatservice.db.UserTotalBalance;
import com.hat.hatservice.db.UserTotalBalanceRepository;
import com.hat.hatservice.exception.BadRequestException;
import com.hat.hatservice.exception.InsufficientBalance;
import com.hat.hatservice.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

interface StakeTypes {
	String SILVER = "SILVER";
	String GOLD = "GOLD";
	String DIAMOND = "DIAMOND";
}

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

	public void stake(StakeRequest stakeRequest) throws NotFoundException, BadRequestException, InsufficientBalance {
		Optional<StakeSettings> stakeSettings;

		switch(stakeRequest.getStakeType()) {
			case StakeTypes.SILVER:
				stakeSettings = stakeSettingsRepository.findStakeSettingsByStakeType(StakeTypes.SILVER);
				stakeSilver(stakeRequest, stakeSettings);
				break;
			case StakeTypes.GOLD :
				stakeSettings = stakeSettingsRepository.findStakeSettingsByStakeType(StakeTypes.GOLD);
				stakeGold(stakeRequest, stakeSettings);
				break;
			case StakeTypes.DIAMOND :
				stakeSettings = stakeSettingsRepository.findStakeSettingsByStakeType(StakeTypes.DIAMOND);
				stakeDiamond(stakeRequest, stakeSettings);
				break;
			default:
				throw new BadRequestException("Bad request type : " + stakeRequest.getStakeType());
		}
	}

	public void stakeSilver(StakeRequest stakeRequest, Optional<StakeSettings> stakeSettingsOptional) throws NotFoundException, InsufficientBalance {
		UserResponse userResponse =  userService.getLoggedUserDetails();
		logger.info("Stake Silver started with : " + userResponse);

		Optional<UserTotalBalance> userTotalBalanceOptional = userTotalBalanceRepository.findById(userResponse.getId());
		UserTotalBalance userTotal = userTotalBalanceOptional.orElseThrow(NotFoundException::new);

		if (userTotal.getLockedBalance() < stakeRequest.getStakeAmount()){
			throw new InsufficientBalance("Insufficient Balance");
		}

		StakeSettings stakeSettings = stakeSettingsOptional.orElseThrow(NotFoundException::new);

		logger.info("Stake Silver amount deleting from User balance : " + stakeRequest.getStakeAmount());
		userTotal.doStake(stakeRequest.getStakeAmount());



	}

	public void stakeGold(StakeRequest stakeRequest, Optional<StakeSettings> repository){

	}

	public void stakeDiamond(StakeRequest stakeRequest, Optional<StakeSettings> repository){

	}
}
