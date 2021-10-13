package com.hat.hatservice.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class TokenValidationRequest {
	@JsonProperty("email")
	@NotNull(message = "email is required")
	private String email;

	@JsonProperty("token")
	@NotNull(message = "token is required")
	private String token;

	public TokenValidationRequest(@NotNull(message = "email is required") String email, @NotNull(message = "token is required") String token) {
		this.email = email;
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public TokenValidationRequest setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getToken() {
		return token;
	}

	public TokenValidationRequest setToken(String token) {
		this.token = token;
		return this;
	}
}
