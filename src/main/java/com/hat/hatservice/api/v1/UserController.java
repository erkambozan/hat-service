package com.hat.hatservice.api.v1;

import com.hat.hatservice.api.dto.AuthenticationRequest;
import com.hat.hatservice.api.dto.AuthenticationResponse;
import com.hat.hatservice.api.dto.TokenValidationRequest;
import com.hat.hatservice.api.dto.TransactionsResponse;
import com.hat.hatservice.api.dto.UserRequest;
import com.hat.hatservice.api.dto.UserResponse;
import com.hat.hatservice.api.dto.WithdrawalRequest;
import com.hat.hatservice.api.dto.WithdrawalResponse;
import com.hat.hatservice.exception.DuplicateException;
import com.hat.hatservice.exception.InvalidTokenException;
import com.hat.hatservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hat")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public UserResponse register(@RequestBody UserRequest request)
			throws DuplicateException {
		return userService.register(request);
	}

	@PostMapping(value = "/validatetoken", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public UserResponse validateToken(@RequestBody TokenValidationRequest request) throws InvalidTokenException {
		return userService.validateToken(request);
	}

	@PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
		return userService.authenticate(authenticationRequest);
	}

	@GetMapping(value = "/transactions/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public List<TransactionsResponse> getTransactionsByUserId(@PathVariable("user_id") UUID userId) {
		return userService.getTransactionsByUserId(userId);
	}

	@GetMapping(value = "/totalbalance", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public Double getTotalBalance() throws Exception {
		return userService.getTotalBalance();
	}

	@PostMapping(value = "/withdraw", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public void createWithdrawalRequest(@RequestBody WithdrawalRequest request) throws Exception {
		userService.createWithdrawalRequest(request);
	}

	@GetMapping(value = "/withdraw", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public List<WithdrawalResponse> getWithdrawalRequestByUserId() throws Exception {
		return userService.getWithdrawalRequestByUserId();
	}

	@DeleteMapping(value = "/withdraw/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public void deleteWithdrawalRequest(@PathVariable("id") UUID id) throws Exception {
		userService.deleteWithdrawalRequest(id);
	}

	@PutMapping(value = "/withdraw/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public void editWithdrawalRequest(@PathVariable("id") UUID id, @RequestBody WithdrawalRequest request) throws Exception {
		userService.editWithdrawal(id, request);
	}
}
