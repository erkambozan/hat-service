package com.hat.hatservice.utils;

import java.util.Optional;

public class OptionalConsumer<T> {
	private final Optional<T> optional;

	private OptionalConsumer(Optional<T> optional) {
		this.optional = optional;
	}

	public static <T> OptionalConsumer<T> of(Optional<T> optional) {
		return new OptionalConsumer<>(optional);
	}

	public T ifPresent(Exception exception) throws Exception {
		if (optional.isEmpty()){
			throw exception;
		}
		return optional.get();
	}

	public void ifPresentReturnException(Exception exception) throws Exception {
		if (optional.isPresent()){
			throw exception;
		}
	}
}
