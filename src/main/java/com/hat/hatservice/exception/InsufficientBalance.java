package com.hat.hatservice.exception;

public class InsufficientBalance extends Exception{
	public InsufficientBalance() {
	}

	public InsufficientBalance(String message) {
		super(message);
	}

	public InsufficientBalance(String message, Throwable cause) {
		super(message, cause);
	}

	public InsufficientBalance(Throwable cause) {
		super(cause);
	}

	public InsufficientBalance(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
