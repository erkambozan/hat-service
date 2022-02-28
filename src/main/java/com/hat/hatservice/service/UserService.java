package com.hat.hatservice.service;

import com.hat.hatservice.api.dto.AuthenticationRequest;
import com.hat.hatservice.api.dto.AuthenticationResponse;
import com.hat.hatservice.api.dto.EarnWithdrawRequest;
import com.hat.hatservice.api.dto.EarnWithdrawResponse;
import com.hat.hatservice.api.dto.ExchangeEarnToWithdrawRequest;
import com.hat.hatservice.api.dto.TokenValidationRequest;
import com.hat.hatservice.api.dto.TransactionsResponse;
import com.hat.hatservice.api.dto.UserRequest;
import com.hat.hatservice.api.dto.UserResponse;
import com.hat.hatservice.api.dto.UserTotalBalanceResponse;
import com.hat.hatservice.api.dto.WithdrawalRequest;
import com.hat.hatservice.api.dto.WithdrawalResponse;
import com.hat.hatservice.config.TokenProvider;
import com.hat.hatservice.db.EarnWithdraw;
import com.hat.hatservice.db.EarnWithdrawRepository;
import com.hat.hatservice.db.Transactions;
import com.hat.hatservice.db.TransactionsRepository;
import com.hat.hatservice.db.User;
import com.hat.hatservice.db.UserRepository;
import com.hat.hatservice.db.UserTotalBalance;
import com.hat.hatservice.db.UserTotalBalanceRepository;
import com.hat.hatservice.db.Withdrawal;
import com.hat.hatservice.db.WithdrawalRepository;
import com.hat.hatservice.exception.BadRequestException;
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
import org.springframework.beans.factory.annotation.Value;
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
	private final EarnWithdrawRepository earnWithdrawRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private TokenProvider tokenProvider;
	@Value("${config.coinPrice}")
	private Double coinPrice;
	@Value("${config.referencePercentage}")
	private Double referencePercentage;

	public UserService(UserRepository userRepository, UserTotalBalanceRepository userTotalBalanceRepository, TransactionsRepository transactionsRepository, WithdrawalRepository withdrawalRepository, EarnWithdrawRepository earnWithdrawRepository) {
		this.userRepository = userRepository;
		this.userTotalBalanceRepository = userTotalBalanceRepository;
		this.transactionsRepository = transactionsRepository;
		this.withdrawalRepository = withdrawalRepository;
		this.earnWithdrawRepository = earnWithdrawRepository;
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

		userTotalBalanceRepository.save(new UserTotalBalance(user.getId(), 0.0, 0.0, 0.0));
		return new UserResponse(
				user.getId(),
				user.getReferenceId(),
				user.getFirstName(),
				user.getLastName(),
				user.getEmail(),
				user.isActive());
	}

	public void updateRole(String roleName, UUID userId) throws NotFoundException {
		Optional<User> userOptional = userRepository.findById(userId);
		if(userOptional.isEmpty()){
			throw new NotFoundException("User Not Found:" + userId);
		}
		userRepository.save(userOptional.get().setRole(roleName));
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

	public void entryTransactionsAmount(UUID userId, Double amount, String title){
		transactionsRepository.save(new Transactions(userId, amount, title));
	}

	public void outputTransactionsAmount(UUID userId, Double amount, String title){
		transactionsRepository.save(new Transactions(userId, -amount, title));
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
		if (request.getWithdrawAmount() > userTotalBalance.getWithdrawableBalance()) throw new InsufficientBalance("Insufficient Balance");
		withdrawalRepository.save(new Withdrawal(userDetails.getId(), request.getWithdrawAmount(), request.getWalletAddress(), "Pending"));
	}

	public void deleteWithdrawalRequest(UUID id) throws Exception {
		User userDetails = OptionalConsumer.of(tokenProvider.getLoggedUser()).ifPresent(new NotFoundException("User Not Found"));
		logger.info("Deleting withdrawal request user id : " + userDetails.getId());
		Withdrawal withdrawal = OptionalConsumer.of(withdrawalRepository.findById(id)).ifPresent(new NotFoundException("Withdraw not found"));
		if(!withdrawal.getStatus().equals("Pending")) throw new BadRequestException("Cannot be deleted without status Pending ");
		withdrawalRepository.deleteById(id);
	}

	public void editWithdrawal(UUID id, WithdrawalRequest request) throws Exception {
		User userLoggedDetails = OptionalConsumer.of(tokenProvider.getLoggedUser()).ifPresent(new NotFoundException("User Not Found"));
		logger.info("Editing withdrawal request user id : " + userLoggedDetails.getId());
		Withdrawal withdrawal = OptionalConsumer.of(withdrawalRepository.findById(id)).ifPresent(new NotFoundException("Withdrawal not found"));
		withdrawal.changeFields(request);
		UserTotalBalance userTotalBalance = OptionalConsumer.of(userTotalBalanceRepository.findById(withdrawal.getUserId())).ifPresent(new NotFoundException("User Total Balance not found"));
		withdrawMoney(request.getWithdrawAmount(), userTotalBalance, request.getWalletAddress());
		logger.info("Withdraw : " + userLoggedDetails.getId());
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

	public void withdrawMoney(Double amount, UserTotalBalance userTotalBalance, String withdrawAddress){
		userTotalBalance.setWithdrawableBalance(userTotalBalance.getWithdrawableBalance() - amount);
		outputTransactionsAmount(userTotalBalance.getUserId(), -amount, "Withdraw to " + withdrawAddress);
		userTotalBalanceRepository.save(userTotalBalance);
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

	public UserTotalBalanceResponse getTotalBalance() throws Exception {
		User userDetails = OptionalConsumer.of(tokenProvider.getLoggedUser()).ifPresent(new NotFoundException("User Not Found"));
		UserTotalBalance userTotalBalance = OptionalConsumer.of(userTotalBalanceRepository.findByUserId(userDetails.getId())).ifPresent(new NotFoundException("Not Found User Balance"));
		return new UserTotalBalanceResponse(userTotalBalance);
	}

	public void referenceProfit(UUID referencedUserId, Double amount) throws Exception {
		UserTotalBalance userTotalBalance = OptionalConsumer.of(userTotalBalanceRepository.findByUserId(referencedUserId)).ifPresent(new NotFoundException("User Not Found"));
		Double profitAmount = (amount / 100) * referencePercentage;
		entryTransactionsAmount(referencedUserId, profitAmount, "Reference Profit");
		userTotalBalance.setWithdrawableBalance(userTotalBalance.getWithdrawableBalance() + profitAmount);
	}

	public void exchangeEarnToWithdraw(ExchangeEarnToWithdrawRequest request) throws Exception {
		final UserResponse userLoggedDetails = getLoggedUserDetails();
		logger.info("Exchanging earn balance to withdraw request user id : " + userLoggedDetails.getId());
		UserTotalBalance userTotalBalance = OptionalConsumer.of(userTotalBalanceRepository.findByUserId(userLoggedDetails.getId())).ifPresent(new NotFoundException("User Total Balance not found"));
		if (userTotalBalance.getEarnBalance() < request.getAmount()) throw new InsufficientBalance("Insufficient Balance");
		userTotalBalance.setEarnBalance(userTotalBalance.getEarnBalance() - request.getAmount());
		userTotalBalance.setWithdrawableBalance(userTotalBalance.getWithdrawableBalance() + request.getAmount());
		userTotalBalanceRepository.save(userTotalBalance);
	}

	public void withdrawEarn(EarnWithdrawRequest request) throws Exception {
		final UserResponse userLoggedDetails = getLoggedUserDetails();
		logger.info("Withdraw Earn working : " + userLoggedDetails.getId());
		if (request.getCoinPrice() * request.getWithdrawAmount() < 50) throw new InsufficientBalance("Earn amount can not less than 50 ");
		UserTotalBalance userTotalBalance = OptionalConsumer.of(userTotalBalanceRepository.findByUserId(userLoggedDetails.getId())).ifPresent(new NotFoundException("User Total Balance not found."));
		if ( (userTotalBalance.getEarnBalance() * coinPrice)  < request.getWithdrawAmount()) throw new InsufficientBalance("Insufficient balance");
		userTotalBalance.setEarnBalance((userTotalBalance.getEarnBalance() * coinPrice) - request.getWithdrawAmount());
		userTotalBalanceRepository.save(userTotalBalance);
		earnWithdrawRepository.save(new EarnWithdraw(userLoggedDetails.getId(), request.getCoinType(), request.getCoinPrice(), request.getWithdrawAddress(), request.getWithdrawAmount(), "Pending"));
	}

	public List<EarnWithdrawResponse> getEarnWithdrawRequestsByUserId() throws NotFoundException {
		final UserResponse userLoggedDetails = getLoggedUserDetails();
		logger.info("Getting earn withdraw user id : " + userLoggedDetails.getId());
		List<EarnWithdraw> earnWithdrawList = earnWithdrawRepository.findAllByUserId(userLoggedDetails.getId());
		List<EarnWithdrawResponse> earnWithdrawResponseList = new ArrayList();
		earnWithdrawList.forEach(earnWithdrawal -> earnWithdrawResponseList.add(new EarnWithdrawResponse(earnWithdrawal)));
		return earnWithdrawResponseList;
	}

	// only Admin
	public List<EarnWithdrawResponse> getEarnWithdrawAll() throws NotFoundException {
		final UserResponse userLoggedDetails = getLoggedUserDetails();
		logger.info("Getting all earn withdraw user id : " + userLoggedDetails.getId());
		Iterable<EarnWithdraw> earnWithdrawList = earnWithdrawRepository.findAll();
		List<EarnWithdrawResponse> earnWithdrawResponseList = new ArrayList();
		earnWithdrawList.forEach(earnWithdrawal -> earnWithdrawResponseList.add(new EarnWithdrawResponse(earnWithdrawal)));
		return earnWithdrawResponseList;
	}

	// only Admin
	public void setEarnWithdrawStatus(UUID id, EarnWithdrawRequest request) throws Exception {
		final UserResponse userLoggedDetails = getLoggedUserDetails();
		logger.info("Setting earn withdraw status user id : " + userLoggedDetails.getId());
		EarnWithdraw earnWithdraw = OptionalConsumer.of(earnWithdrawRepository.findById(id)).ifPresent(new NotFoundException("Earn Withdraw Not found"));
		earnWithdraw.setStatus(request.getStatus());
		earnWithdrawRepository.save(earnWithdraw);
	}

	public void deleteEarnWithdraw(UUID id) throws Exception {
		final UserResponse userLoggedDetails = getLoggedUserDetails();
		logger.info("Deleting earn withdraw user id : " + userLoggedDetails.getId());
		EarnWithdraw earnWithdraw = OptionalConsumer.of(earnWithdrawRepository.findById(id)).ifPresent(new NotFoundException("Earn Withdraw Not found"));
		if(!earnWithdraw.getStatus().equals("Pending")) throw new BadRequestException("Cannot be deleted without status Pending ");
		earnWithdrawRepository.delete(earnWithdraw);
	}

	public Integer userReferenceCount() throws NotFoundException {
		final UserResponse userLoggedDetails = getLoggedUserDetails();
		List<User> userList = userRepository.findByReferenceId(userLoggedDetails.getId());
		return userList.size();
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
