package com.hat.hatservice.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VerificationRequest {
	private String email;
	@JsonProperty("verification_code")
	private Integer verificationCode;

	public VerificationRequest() {
	}

	public VerificationRequest(Integer verificationCode) {
		this.verificationCode = verificationCode;
	}

	public VerificationRequest(String email, Integer verificationCode) {
		this.email = email;
		this.verificationCode = verificationCode;
	}

	public Integer getVerificationCode() {
		return verificationCode;
	}

	public VerificationRequest setVerificationCode(Integer verificationCode) {
		this.verificationCode = verificationCode;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public VerificationRequest setEmail(String email) {
		this.email = email;
		return this;
	}
}
