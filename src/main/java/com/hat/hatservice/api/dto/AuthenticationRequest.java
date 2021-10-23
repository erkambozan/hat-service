package com.hat.hatservice.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class AuthenticationRequest {
	@JsonProperty("username")
	@NotNull(message = "username required")
	private String username;

	@JsonProperty("password")
	@NotNull(message = "password required")
	private String password;

	public String getUsername() {
		return username;
	}

	public AuthenticationRequest setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public AuthenticationRequest setPassword(String password) {
		this.password = password;
		return this;
	}
}
