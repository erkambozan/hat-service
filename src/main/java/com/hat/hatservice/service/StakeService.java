package com.hat.hatservice.service;

import com.hat.hatservice.api.dto.StakeRequest;
import com.hat.hatservice.api.dto.StakeResponse;
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
import com.hat.hatservice.utils.OptionalConsumer;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

	public void stake(StakeRequest stakeRequest) throws Exception {
		final UserResponse userLoggedDetails = userService.getLoggedUserDetails();
		logger.info("Stake started with : " + userLoggedDetails.getId());
		UserTotalBalance userTotalBalance = getUserWithdrawableBalance(stakeRequest, userLoggedDetails);
		StakeSettings stakeSettings = getStakeSettings(stakeRequest);
		stakeMaker(stakeRequest, userLoggedDetails, userTotalBalance, stakeSettings);
	}

	private void stakeMaker(StakeRequest stakeRequest, UserResponse userLoggedDetails, UserTotalBalance userTotalBalanceOptional, StakeSettings stakeSettings) throws ParseException {
		Double expiryStakeAmount = stakeRequest.getStakeAmount() + (stakeRequest.getStakeAmount() * stakeSettings.getStakePercentage() / 100);
		DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(customFormatter.format(LocalDate.now().plusDays(stakeSettings.getExpiryStakeTime())));
		Stake stake = stakeRepository.save(new Stake(userLoggedDetails.getId(), stakeRequest.getStakeAmount(), expiryStakeAmount, stakeSettings.getExpiryStakeTime(), stakeSettings.getStakePercentage(), stakeSettings.getStakeType(), true, endDate));
		logger.info("Stake amount deleting from User balance : " + stakeRequest.getStakeAmount());
		doStake(stake.getId(), stakeRequest.getStakeAmount(), userTotalBalanceOptional);
	}

	private UserTotalBalance getUserWithdrawableBalance(StakeRequest stakeRequest, UserResponse userLoggedDetails) throws Exception {
		UserTotalBalance userTotalBalance = OptionalConsumer.of(userTotalBalanceRepository.findByUserId(userLoggedDetails.getId())).ifPresent(new NotFoundException("User balance not found"));
		if (userTotalBalance.getWithdrawableBalance() < stakeRequest.getStakeAmount()) throw new InsufficientBalance("Insufficient Balance");
		return userTotalBalance;
	}

	private StakeSettings getStakeSettings(StakeRequest stakeRequest) throws NotFoundException, InsufficientBalance {
		Optional<StakeSettings> stakeSettingsOptional = stakeSettingsRepository.findStakeSettingsByStakeType(stakeRequest.getStakeType());
		if (stakeSettingsOptional.isEmpty()) throw new NotFoundException("Stake Type Not Found");
		StakeSettings stakeSettings = stakeSettingsOptional.get();
		if (stakeRequest.getStakeAmount() < stakeSettings.getMinimumLimit() && stakeRequest.getStakeAmount() > stakeSettings.getMaximumLimit())
			throw new InsufficientBalance("Insufficient Limit");
		return stakeSettings;
	}

	public StakeSettingsResponse createStakeSettings(StakeSettingsRequest request) throws Exception {
		logger.info("Stake Settings Creating : " + request.getStakeType());
		OptionalConsumer.of(stakeSettingsRepository.findStakeSettingsByStakeType(request.getStakeType())).ifPresentReturnException(new DuplicateException("Stake settings already exist"));
		StakeSettings stakeSettings = stakeSettingsRepository.save(new StakeSettings(request.getExpiryStakeTime(), request.getStakePercentage(), request.getStakeType(), request.getMinimumLimit(), request.getMaximumLimit()));
		return new StakeSettingsResponse(stakeSettings.getId(), stakeSettings.getExpiryStakeTime(), stakeSettings.getStakePercentage(), stakeSettings.getStakeType(), stakeSettings.getMinimumLimit(), stakeSettings.getMaximumLimit());
	}

	public void updateStakeSettings(StakeSettingsRequest request, UUID id) throws NotFoundException {
		logger.info("Updating Stake Settings : " + id);
		StakeSettings stakeSettings = stakeSettingsRepository.findById(id).orElseThrow(NotFoundException::new);
		mapper.map(request, stakeSettings);
		stakeSettingsRepository.save(stakeSettings);
	}

	public List<StakeSettingsResponse> getStakeSettingsAll() {
		logger.info("Getting all stake settings");
		Iterable<StakeSettings> stakeList = stakeSettingsRepository.findAll();
		List<StakeSettingsResponse> stakeResponseList = new ArrayList();
		stakeList.forEach(stakeSettings -> stakeResponseList.add(new StakeSettingsResponse(stakeSettings)));
		return stakeResponseList;
	}

	public List<StakeResponse> findStakesByUserId() throws NotFoundException {
		final UserResponse userLoggedDetails = userService.getLoggedUserDetails();
		logger.info("Get Stake with user id : " + userLoggedDetails.getId());
		List<Stake> stakeList = stakeRepository.findAllByUserId(userLoggedDetails.getId());
		List<StakeResponse> stakeResponseList = new ArrayList();
		stakeList.forEach(stake -> stakeResponseList.add(new StakeResponse(stake)));
		return stakeResponseList;
	}

	public List<StakeResponse> findAllStakes() {
		logger.info("Getting all stakes");
		Iterable<Stake> stakeList = stakeRepository.findAll();
		List<StakeResponse> stakeResponseList = new ArrayList();
		stakeList.forEach(stake -> stakeResponseList.add(new StakeResponse(stake)));
		return stakeResponseList;
	}


	public void stakeProfit(Double stakeStartedAmount, Double stakeExpiryAmount, UserTotalBalance userTotalBalance) {
		Double stakeEarnAmount = stakeExpiryAmount - stakeStartedAmount;
		userTotalBalance.setEarnBalance(stakeEarnAmount);
		userTotalBalance.setWithdrawableBalance(userTotalBalance.getWithdrawableBalance() + stakeStartedAmount);
		userTotalBalance.setLockedBalance(userTotalBalance.getLockedBalance() - stakeStartedAmount);
		userTotalBalanceRepository.save(userTotalBalance);
	}

	public void finishStake(UUID stakeId, StakeRepository stakeRepository) throws Exception {
		logger.info("Stake finishing stake id : " + stakeId);
		Stake stake = OptionalConsumer.of(stakeRepository.findById(stakeId)).ifPresent(new NotFoundException("Stake not found."));
		stakeRepository.save(stake.setStakeStatus(false));
	}

	public void referenceProfit(Double amount, UserTotalBalance userTotalBalance) {
		userTotalBalance.setWithdrawableBalance(userTotalBalance.getEarnBalance() + amount);
		userTotalBalanceRepository.save(userTotalBalance);
	}

	public void doStake(UUID stakeId, Double amount, UserTotalBalance userTotalBalance) {
		userTotalBalance.setWithdrawableBalance(userTotalBalance.getWithdrawableBalance() - amount);
		userTotalBalance.setLockedBalance(userTotalBalance.getLockedBalance() + amount);
		userService.outputTransactionsAmount(userTotalBalance.getUserId(), stakeId, amount, "Stake");
		userTotalBalanceRepository.save(userTotalBalance);
	}


}