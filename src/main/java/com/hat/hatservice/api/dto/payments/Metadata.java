package com.hat.hatservice.api.dto.payments;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Metadata {
	String email;
	String name;
	String user;

	@JsonProperty("email")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonProperty("name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("user")
	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
