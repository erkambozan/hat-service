package com.hat.hatservice.api.v1;

import com.hat.hatservice.api.dto.EmailRequest;
import com.hat.hatservice.api.dto.VerificationRequest;
import com.hat.hatservice.exception.EmailCouldNotSendException;
import com.hat.hatservice.exception.NotFoundException;
import com.hat.hatservice.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hat/email")
public class EmailController {
	private final EmailService emailService;

	public EmailController(EmailService emailService) {
		this.emailService = emailService;
	}

	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public void sendEmail(@RequestBody EmailRequest request)
			throws NotFoundException, EmailCouldNotSendException {
		emailService.sendEmail(request);
	}

	@GetMapping(value = "/sendcode", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public void createVerificationCode()
			throws Exception {
		emailService.createVerificationCodeLoggedUser();
	}

		@PostMapping(value = "/sendcodenotloggeduser", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public void createVerificationCodeNotLoggedUser(@RequestBody VerificationRequest request)
			throws Exception {
		emailService.createVerificationCodeNotLoggedUser(request);
	}

	@PostMapping(value = "/verifycode", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public void verifyCode(@RequestBody VerificationRequest request)
			throws Exception {
		emailService.verifyCode(request);
	}

	@PostMapping(value = "/verifycodenotlogged", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public void verifyCodeNotLoggedUser(@RequestBody VerificationRequest request)
			throws Exception {
		emailService.verifyCodeNotLoggedUser(request);
	}

	@PostMapping(value = "/verifyemail", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public void verifyEmail(@RequestBody VerificationRequest request)
			throws Exception {
		emailService.verifyCode(request);
	}
}
