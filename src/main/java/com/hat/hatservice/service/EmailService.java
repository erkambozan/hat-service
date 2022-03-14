package com.hat.hatservice.service;

import com.hat.hatservice.api.dto.EmailRequest;
import com.hat.hatservice.api.dto.UserResponse;
import com.hat.hatservice.api.dto.VerificationRequest;
import com.hat.hatservice.db.EmailVerification;
import com.hat.hatservice.db.EmailVerificationRepository;
import com.hat.hatservice.db.User;
import com.hat.hatservice.db.UserRepository;
import com.hat.hatservice.exception.BadRequestException;
import com.hat.hatservice.exception.EmailCouldNotSendException;
import com.hat.hatservice.exception.NotFoundException;
import com.hat.hatservice.utils.OptionalConsumer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;


@Service
public class EmailService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private final EmailVerificationRepository emailVerificationRepository;
	private final UserRepository userRepository;
	private final UserService userService;
	private final JavaMailSender mailSender;
	@Value("${config.ownerEmail}")
	private String ownerEmail;

	public EmailService(EmailVerificationRepository emailVerificationRepository, UserRepository userRepository, UserService userService, JavaMailSender mailSender) {
		this.emailVerificationRepository = emailVerificationRepository;
		this.userRepository = userRepository;
		this.userService = userService;
		this.mailSender = mailSender;
	}

	public void createVerificationCodeLoggedUser() throws Exception {
		User user = userService.getLoggedUser();
		createVerificationCode(user);
	}

	public void createVerificationCodeNotLoggedUser(VerificationRequest request) throws Exception {
		User user = OptionalConsumer.of(userRepository.findUserByEmail(request.getEmail())).ifPresent(new NotFoundException("User not found"));
		createVerificationCode(user);
	}

	private void createVerificationCode(User user) throws Exception {
		Random random = new Random();
		Integer verificationCode = random.nextInt(999999);
		Date endTime = endMinuteGenerator();
		EmailVerification emailVerification = OptionalConsumer.of(emailVerificationRepository.findByUserId(user.getId())).ifPresent(new NotFoundException("User Not Found"));
		emailVerification.setVerificationCode(verificationCode).setEndTime(endTime);
		emailVerificationRepository.save(emailVerification);
		sendEmailVerification(user.getEmail(), verificationCode);
	}

	public void verifyCode(VerificationRequest request) throws Exception {
		User user = userService.getLoggedUser();
		verifyCodeControl(request, user);
	}

	public void verifyCodeNotLoggedUser(VerificationRequest request) throws Exception {
		User user = OptionalConsumer.of(userRepository.findUserByEmail(request.getEmail())).ifPresent(new NotFoundException("User not found"));
		verifyCodeControl(request, user);
	}

	private void verifyCodeControl(VerificationRequest request, User user) throws Exception {
		EmailVerification emailVerification = OptionalConsumer.of(emailVerificationRepository.findByUserId(user.getId())).ifPresent(new NotFoundException("User Not Found"));
		if (!request.getVerificationCode().equals(emailVerification.getVerificationCode())) throw new BadRequestException("Verification code not matched");
		verifyCodeTime(emailVerification);
		emailVerificationRepository.save(emailVerification.setVerificationCode(0));
	}

	public void sendEmailVerification(String userEmail, Integer verificationCode) throws EmailCouldNotSendException {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(ownerEmail);
			helper.setTo(userEmail);
			helper.setSubject("Helt Coin Support");
			helper.setText("Helt Coin Verification Code : " + verificationCode);
			this.mailSender.send(message);
		} catch (MailException | MessagingException ex) {
			logger.error("Failed to send the email. request:" + userEmail, ex);
			throw new EmailCouldNotSendException("Failed to send the email", ex);
		}
	}

	public void sendEmail(EmailRequest request) throws NotFoundException, EmailCouldNotSendException {
		UserResponse user = userService.getLoggedUserDetails();
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(ownerEmail);
			helper.setTo(ownerEmail);
			helper.setSubject(request.getTitle());
			helper.setText(" User Id " + user.getId() + " User Email : " + user.getEmail() + " Description " + request.getDescription());
			this.mailSender.send(message);
		} catch (MailException | MessagingException ex) {
			logger.error("Failed to send the email. request:" + user.getEmail(), ex);
			throw new EmailCouldNotSendException("Failed to send the email", ex);
		}
	}


	private void verifyCodeTime(EmailVerification emailVerification) throws BadRequestException {
		Calendar date = Calendar.getInstance();
		long timeInSecs = date.getTimeInMillis();
		Date timeNow = new Date(timeInSecs);
		if (emailVerification.getEndTime().after(timeNow)) throw new BadRequestException("Email verification timeout");
	}

	private Date endMinuteGenerator() {
		Calendar date = Calendar.getInstance();
		long timeInSecs = date.getTimeInMillis();
		return new Date(timeInSecs + (2 * 60 * 1000));
	}
}
