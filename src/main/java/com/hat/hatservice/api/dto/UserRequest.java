package com.hat.hatservice.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import java.util.UUID;

public class UserRequest {
	@JsonProperty("reference_id")
	private UUID referenceId;
	@JsonProperty("first_name")
	private String firstName;
	@JsonProperty("last_name")
	private String lastName;
	@JsonProperty("email")
	@NotNull()
	private String email;
	@JsonProperty("password")
	@NotNull()
	private String password;
	@JsonProperty("secret")
	private String secret;
	@JsonProperty("active")
	private boolean active;

	public UserRequest() {
	}

	public UserRequest(UUID referenceId, String firstName, String lastName, String email, String password, String secret, boolean active) {
		this.referenceId = referenceId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.secret = secret;
		this.active = active;
	}

	public UUID getReferenceId() {
		return referenceId;
	}

	public UserRequest setReferenceId(UUID referenceId) {
		this.referenceId = referenceId;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public UserRequest setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public UserRequest setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public UserRequest setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public UserRequest setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getSecret() {
		return secret;
	}

	public UserRequest setSecret(String secret) {
		this.secret = secret;
		return this;
	}

	public boolean isActive() {
		return active;
	}

	public UserRequest setActive(boolean active) {
		this.active = active;
		return this;
	}
}
