package com.hat.hatservice.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class UserResponse {
	private UUID id;
	@JsonProperty("reference_id")
	private UUID referenceId;
	@JsonProperty("firstName")
	private String firstName;
	@JsonProperty("lastName")
	private String lastName;
	@JsonProperty("email")
	private String email;
	@JsonProperty("active")
	private boolean active;

	public UserResponse() {
	}

	public UserResponse(UUID id, UUID referenceId, String firstName, String lastName, String email, boolean active) {
		this.id = id;
		this.referenceId = referenceId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.active = active;
	}

	public UUID getId() {
		return id;
	}

	public UserResponse setId(UUID id) {
		this.id = id;
		return this;
	}

	public UUID getReferenceId() {
		return referenceId;
	}

	public UserResponse setReferenceId(UUID referenceId) {
		this.referenceId = referenceId;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public UserResponse setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public UserResponse setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public UserResponse setEmail(String email) {
		this.email = email;
		return this;
	}

	public boolean isActive() {
		return active;
	}

	public UserResponse setActive(boolean active) {
		this.active = active;
		return this;
	}
}
