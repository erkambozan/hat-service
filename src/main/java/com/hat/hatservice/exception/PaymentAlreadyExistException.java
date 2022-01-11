package com.hat.hatservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class PaymentAlreadyExistException extends Exception{

	public PaymentAlreadyExistException() {
	}

	public PaymentAlreadyExistException(String message) {
		super(message);
	}

	public PaymentAlreadyExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public PaymentAlreadyExistException(Throwable cause) {
		super(cause);
	}

	public PaymentAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
