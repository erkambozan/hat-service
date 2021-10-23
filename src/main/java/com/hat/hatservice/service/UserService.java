package com.hat.hatservice.service;

import com.hat.hatservice.api.dto.AuthenticationRequest;
import com.hat.hatservice.api.dto.AuthenticationResponse;
import com.hat.hatservice.api.dto.TokenValidationRequest;
import com.hat.hatservice.api.dto.UserRequest;
import com.hat.hatservice.api.dto.UserResponse;
import com.hat.hatservice.config.TokenProvider;
import com.hat.hatservice.db.User;
import com.hat.hatservice.db.UserRepository;
import com.hat.hatservice.exception.DuplicateException;
import com.hat.hatservice.exception.InvalidTokenException;
import de.taimos.totp.TOTP;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final TokenProvider tokenProvider;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.tokenProvider = tokenProvider;
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

}
