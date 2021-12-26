package com.hat.hatservice.service;

import com.hat.hatservice.api.dto.AuthenticationRequest;
import com.hat.hatservice.api.dto.AuthenticationResponse;
import com.hat.hatservice.api.dto.TokenValidationRequest;
import com.hat.hatservice.api.dto.TransactionsResponse;
import com.hat.hatservice.api.dto.UserRequest;
import com.hat.hatservice.api.dto.UserResponse;
import com.hat.hatservice.api.dto.WithdrawalRequest;
import com.hat.hatservice.api.dto.WithdrawalResponse;
import com.hat.hatservice.config.TokenProvider;
import com.hat.hatservice.db.Transactions;
import com.hat.hatservice.db.TransactionsRepository;
import com.hat.hatservice.db.User;
import com.hat.hatservice.db.UserRepository;
import com.hat.hatservice.db.UserTotalBalance;
import com.hat.hatservice.db.UserTotalBalanceRepository;
import com.hat.hatservice.db.Withdrawal;
import com.hat.hatservice.db.WithdrawalRepository;
import com.hat.hatservice.exception.DuplicateException;
import com.hat.hatservice.exception.InsufficientBalance;
import com.hat.hatservice.exception.InvalidTokenException;
import com.hat.hatservice.exception.NotFoundException;
import com.hat.hatservice.utils.OptionalConsumer;
import de.taimos.totp.TOTP;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	private final UserRepository userRepository;
	private final UserTotalBalanceRepository userTotalBalanceRepository;
	private final TransactionsRepository transactionsRepository;
	private final WithdrawalRepository withdrawalRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private TokenProvider tokenProvider;

	public UserService(UserRepository userRepository, UserTotalBalanceRepository userTotalBalanceRepository, TransactionsRepository transactionsRepository, WithdrawalRepository withdrawalRepository) {
		this.userRepository = userRepository;
		this.userTotalBalanceRepository = userTotalBalanceRepository;
		this.transactionsRepository = transactionsRepository;
		this.withdrawalRepository = withdrawalRepository;
	}

	public UserResponse register(UserRequest request) throws DuplicateException {
		Optional<User> userOptional = userRepository.findUserByEmail(request.getEmail());

		if (userOptional.isPresent()) {
			throw new DuplicateException("Duplicate user with email:" + request.getEmail());
		}

		if (request.getPassword() != null) {
			request.setPassword(passwordEncoder.encode(request.getPassword()));
		}

		User user = userRepository.save(new User(
				request.getReferenceId(),
				request.getFirstName(),
				request.getLastName(),
				request.getEmail(),
				request.getPassword(),
				"",
				true
		));

		userTotalBalanceRepository.save(new UserTotalBalance(user.getId(), 0.0));
		return new UserResponse(
				user.getId(),
				user.getReferenceId(),
				user.getFirstName(),
				user.getLastName(),
				user.getEmail(),
				user.isActive());
	}

	protected void authenticate(String username, String password) throws BadCredentialsException, DisabledException {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}

	public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws BadCredentialsException, DisabledException {
		this.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserResponse user = this.getUserDetails(authenticationRequest.getUsername());
		return new AuthenticationResponse(user.getId(), user.getReferenceId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.isActive(),
				tokenProvider.createJWTToken(user.getEmail()));
	}

	public UserResponse getUserDetails(String username) throws UsernameNotFoundException {
		User u = loadUserDetails(username);
		return new UserResponse(
				u.getId(),
				u.getReferenceId(),
				u.getFirstName(),
				u.getLastName(),
				u.getEmail(),
				u.isActive());
	}

	public User loadUserDetails(String username) throws UsernameNotFoundException {
		Optional<User> applicationUser = userRepository.findUserByEmail(username);
		if (applicationUser.isEmpty()) {
			logger.warn("User not found:" + username);
			throw new UsernameNotFoundException(username);
		}
		return applicationUser.get();
	}

	public UserResponse validateToken(TokenValidationRequest request) throws InvalidTokenException {
		logger.info("Validating token :" + request.getToken() + " email:" + request.getEmail());
		User user = this.loadUserDetails(request.getEmail());
		byte[] bytes = new Base32().decode(user.getSecret());
		if (!TOTP.validate(Hex.encodeHexString(bytes), request.getToken())) {
			throw new InvalidTokenException("Invalid token");
		}
		return new UserResponse(
				user.getId(),
				user.getReferenceId(),
				user.getFirstName(),
				user.getLastName(),
				user.getEmail(),
				user.isActive());
	}

	public UserResponse getLoggedUserDetails() throws NotFoundException {
		Optional<User> userDetails = tokenProvider.getLoggedUser();

		User user = userDetails.orElseThrow(() -> new NotFoundException("User Not Found"));

		return new UserResponse(user.getId(), user.getReferenceId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.isActive());
	}

	public void entryTransactionsAmount(UUID userId, Double amount){
		transactionsRepository.save(new Transactions(userId, amount));
	}

	public void outputTransactionsAmount(UUID userId, Double amount){
		transactionsRepository.save(new Transactions(userId, -amount));
	}

	public List<TransactionsResponse> getTransactionsByUserId(UUID userId){
		logger.info("Get Stake with user id : " + userId);
		List<Transactions> transactionsList = transactionsRepository.findAllByUserId(userId);
		List<TransactionsResponse> transactionsResponseList = new ArrayList();
		transactionsList.forEach(transaction -> transactionsResponseList.add(new TransactionsResponse(transaction)));
		return transactionsResponseList;
	}

	public List<TransactionsResponse> getAllTransactions(){
		Iterable<Transactions> transactionsList = transactionsRepository.findAll();
		List<TransactionsResponse> transactionsResponseList = new ArrayList();
		transactionsList.forEach(transaction -> transactionsResponseList.add(new TransactionsResponse(transaction)));
		return transactionsResponseList;
	}

	public void createWithdrawalRequest(WithdrawalRequest request) throws Exception {
		User userDetails = OptionalConsumer.of(tokenProvider.getLoggedUser()).ifPresent(new NotFoundException("User Not Found"));
		UserTotalBalance userTotalBalance = OptionalConsumer.of(userTotalBalanceRepository.findByUserId(userDetails.getId())).ifPresent(new NotFoundException("Not Found User Balance"));
		if (request.getWithdrawAmount() > userTotalBalance.getTotalBalance()) throw new InsufficientBalance("Insufficient Balance");
		withdrawalRepository.save(new Withdrawal(userDetails.getId(), request.getWithdrawAmount(), request.getWalletAddress(), "Pending"));
	}

	public void deleteWithdrawalRequest(UUID id) throws Exception {
		User userDetails = OptionalConsumer.of(tokenProvider.getLoggedUser()).ifPresent(new NotFoundException("User Not Found"));
		logger.info("Deleting withdrawal request user id : " + userDetails.getId());
		withdrawalRepository.deleteById(id);
	}

	public void editWithdrawal(UUID id, WithdrawalRequest request) throws Exception {
		User userDetails = OptionalConsumer.of(tokenProvider.getLoggedUser()).ifPresent(new NotFoundException("User Not Found"));
		logger.info("Editing withdrawal request user id : " + userDetails.getId());
		Withdrawal withdrawal = OptionalConsumer.of(withdrawalRepository.findById(id)).ifPresent(new NotFoundException("Withdrawal not found"));
		withdrawal.changeFields(request);
		withdrawalRepository.save(withdrawal);
	}

	public List<WithdrawalResponse> getWithdrawalRequestByUserId() throws Exception {
		User userDetails = OptionalConsumer.of(tokenProvider.getLoggedUser()).ifPresent(new NotFoundException("User Not Found"));
		logger.info("Get withdrawal request user id : " + userDetails.getId());
		List<Withdrawal> withdrawalList = withdrawalRepository.findAllByUserId(userDetails.getId());
		List<WithdrawalResponse> withdrawalResponseList = new ArrayList();
		withdrawalList.forEach(withdrawal -> withdrawalResponseList.add(new WithdrawalResponse(withdrawal)));
		return withdrawalResponseList;
	}

	public List<WithdrawalResponse> getWithdrawalRequestAll(){
		logger.info("Get all withdrawal requests");
		Iterable<Withdrawal> withdrawalsList = withdrawalRepository.findAll();
		List<WithdrawalResponse> withdrawalResponseList = new ArrayList();
		withdrawalsList.forEach(withdrawal -> withdrawalResponseList.add(new WithdrawalResponse(withdrawal)));
		return withdrawalResponseList;
	}

	public void changeWithdrawal(UUID id, String status) throws Exception {
		Withdrawal withdrawal = OptionalConsumer.of(withdrawalRepository.findById(id)).ifPresent(new NotFoundException("Withdrawal not found"));
		withdrawal.setStatus(status);
		withdrawalRepository.save(withdrawal);
	}

	public Double getTotalBalance() throws Exception {
		User userDetails = OptionalConsumer.of(tokenProvider.getLoggedUser()).ifPresent(new NotFoundException("User Not Found"));
		UserTotalBalance userTotalBalance = OptionalConsumer.of(userTotalBalanceRepository.findByUserId(userDetails.getId())).ifPresent(new NotFoundException("Not Found User Balance"));
		return userTotalBalance.getTotalBalance();
	}

	public UserService setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		return this;
	}

	public UserService setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		return this;
	}

	public UserService setTokenProvider(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
		return this;
	}
}
