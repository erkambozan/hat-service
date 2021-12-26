package com.hat.hatservice.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hat.hatservice.db.Withdrawal;

import java.util.UUID;

public class WithdrawalResponse {
	private UUID id;
	@JsonProperty("userId")
	private UUID userId;
	@JsonProperty("withdraw_amount")
	private Integer withdrawAmount;
	@JsonProperty("wallet_address")
	private String walletAddress;
	private String status;

	public WithdrawalResponse() {
	}

	public WithdrawalResponse(Withdrawal withdrawal) {
		this.id = withdrawal.getId();
		this.userId = withdrawal.getUserId();
		this.withdrawAmount = withdrawal.getWithdrawAmount();
		this.walletAddress = withdrawal.getWalletAddress();
		this.status = withdrawal.getStatus();
	}

	public UUID getId() {
		return id;
	}

	public WithdrawalResponse setId(UUID id) {
		this.id = id;
		return this;
	}

	public UUID getUserId() {
		return userId;
	}

	public WithdrawalResponse setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public Integer getWithdrawAmount() {
		return withdrawAmount;
	}

	public WithdrawalResponse setWithdrawAmount(Integer withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
		return this;
	}

	public String getWalletAddress() {
		return walletAddress;
	}

	public WithdrawalResponse setWalletAddress(String walletAddress) {
		this.walletAddress = walletAddress;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public WithdrawalResponse setStatus(String status) {
		this.status = status;
		return this;
	}
}
