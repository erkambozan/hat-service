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
import com.hat.hatservice.exception.UserNotFoundException;
import de.taimos.totp.TOTP;
import javassist.NotFoundException;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;

	private final AuthenticationManager authenticationManager;

	private TokenProvider tokenProvider;

	public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
		this.tokenProvider = tokenProvider;
	}

	public UserResponse register(UserRequest request) throws DuplicateException {

		logger.info("User registration : " + request);

		Optional<User> userOptional = userRepository.findUserByEmail(request.getEmail());

		if (userOptional.isPresent()){
			throw new DuplicateException("User Already Exist : " + userOptional.get().getEmail());
		}

		User user = userRepository.save(new User(request.getReferenceId(), request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword(), request.getSecret(), true));


		return new UserResponse(
				user.getId(),
				user.getReferenceId(),
				user.getFirstName(),
				user.getLastName(),
				user.getEmail(),
				user.getSecret(),
				user.isActive()
		);
	}

	protected void authenticate(String username, String password) throws BadCredentialsException, DisabledException {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}

	public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws BadCredentialsException, DisabledException, NotFoundException, UserNotFoundException {
		this.authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
		final UserResponse user = this.getUserDetails(authenticationRequest.getEmail());
		return new AuthenticationResponse(user.getId(), user.getReferenceId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getSecret(), user.isActive(),
				tokenProvider.createJWTToken(user.getEmail()));
	}

	public UserResponse getUserDetails(String username) throws NotFoundException, UserNotFoundException {
		User u = loadUserDetails(username);
		return new UserResponse(
				u.getId(),
				u.getReferenceId(),
				u.getFirstName(),
				u.getLastName(),
				u.getEmail(),
				u.getSecret(),
				u.isActive());
	}

	public User loadUserDetails(String username) throws NotFoundException, UserNotFoundException {
		Optional<User> applicationUser = userRepository.findUserByEmail(username);
		if (!applicationUser.isPresent()) {
			logger.warn("User not found:" + username);
			throw new UserNotFoundException(username);
		}
		return applicationUser.get();
	}

	public UserResponse getLoggedUserDetails() throws UserNotFoundException {
		Optional<User> userDetails = tokenProvider.getLoggedUser();

		User user = userDetails.orElseThrow(() -> new UserNotFoundException("User Not Found"));

		return new UserResponse(
				user.getId(),
				user.getReferenceId(),
				user.getFirstName(),
				user.getLastName(),
				user.getEmail(),
				user.getSecret(),
				user.isActive());
	}

	public UserResponse validateToken(TokenValidationRequest request) throws InvalidTokenException, NotFoundException, UserNotFoundException {
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
				user.getSecret(),
				user.isActive());
	}
}
