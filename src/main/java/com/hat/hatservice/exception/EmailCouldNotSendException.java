package com.hat.hatservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class EmailCouldNotSendException extends Exception{
	public EmailCouldNotSendException() {
	}

	public EmailCouldNotSendException(String message) {
		super(message);
	}

	public EmailCouldNotSendException(String message, Throwable cause) {
		super(message, cause);
	}
}
