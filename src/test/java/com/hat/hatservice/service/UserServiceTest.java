package com.hat.hatservice.service;

import com.hat.hatservice.api.dto.UserRequest;
import com.hat.hatservice.api.dto.UserResponse;
import com.hat.hatservice.config.TokenProvider;
import com.hat.hatservice.db.User;
import com.hat.hatservice.db.UserRepository;
import com.hat.hatservice.db.UserTotalBalance;
import com.hat.hatservice.db.UserTotalBalanceRepository;
import com.hat.hatservice.exception.DuplicateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.mock;


class UserServiceTest {
	private UserRepository userRepository;
	private UserTotalBalanceRepository userTotalBalanceRepository;
	private PasswordEncoder passwordEncoder;
	private AuthenticationManager authenticationManager;
	private TokenProvider tokenProvider;

	private UserService userService;

	private UserRequest userRequest;

	private UUID userId = UUID.fromString("1d1a8f89-7a2d-4869-a077-0e5ac8774052");
	private UUID referenceId = UUID.fromString("ac895599-11be-4e66-984c-c0980a7801b5");

	@BeforeEach
	void setUp() {
		userRepository = mock(UserRepository.class);
		userTotalBalanceRepository = mock(UserTotalBalanceRepository.class);
		passwordEncoder = mock(PasswordEncoder.class);
		authenticationManager = mock(AuthenticationManager.class);
		tokenProvider = mock(TokenProvider.class);
		userService = new UserService(userRepository, userTotalBalanceRepository);
		userService.setPasswordEncoder(passwordEncoder);
		userService.setAuthenticationManager(authenticationManager);
		userService.setTokenProvider(tokenProvider);
		userRequest = new UserRequest(referenceId, "John", "Doe", "john.doe@gmail.com", "123", "", true);
	}

	@Test
	void register() throws DuplicateException {
		User user = new User(userRequest.getReferenceId(), userRequest.getFirstName(),  userRequest.getLastName(), userRequest.getEmail(), userRequest.getPassword(), userRequest.getSecret(), userRequest.isActive());
		user.setId(userId);

		when(userRepository.findUserByEmail(userRequest.getEmail())).thenReturn(Optional.empty());
		when(userRepository.save(any())).thenReturn(user);

		UserResponse response = userService.register(userRequest);

		assertAll("Create User",
				() -> assertEquals(userRequest.getFirstName(), response.getFirstName()),
				() -> assertEquals(userRequest.getLastName(), response.getLastName()),
				() -> assertEquals(userRequest.getEmail(), response.getEmail())
		);
	}
}