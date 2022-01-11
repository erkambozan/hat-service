package com.hat.hatservice.api.dto.payments;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Block {
	int height;
	String hash;
	int confirmations;
	int confirmations_required;

	@JsonProperty("height")
	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@JsonProperty("hash")
	public String getHash() {
		return this.hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	@JsonProperty("confirmations")
	public int getConfirmations() {
		return this.confirmations;
	}

	public void setConfirmations(int confirmations) {
		this.confirmations = confirmations;
	}

	@JsonProperty("confirmations_required")
	public int getConfirmations_required() {
		return this.confirmations_required;
	}

	public void setConfirmations_required(int confirmations_required) {
		this.confirmations_required = confirmations_required;
	}
}
