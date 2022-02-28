package com.hat.hatservice.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class AuthenticationResponse extends UserResponse{
	@JsonProperty("token")
	private String jwtToken;

	public AuthenticationResponse(UUID id, UUID referenceId, String firstName, String lastName, String email, boolean active, String jwtToken) {
		super(id, referenceId, firstName, lastName, email,  active);
		this.jwtToken = jwtToken;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public AuthenticationResponse setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
		return this;
	}
}
