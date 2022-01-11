package com.hat.hatservice.api.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class WithdrawalRequest {
	@JsonProperty("withdraw_amount")
	private Double withdrawAmount;
	@JsonProperty("wallet_address")
	private String walletAddress;

	public WithdrawalRequest() {
	}

	public WithdrawalRequest(Double withdrawAmount, String walletAddress) {
		this.withdrawAmount = withdrawAmount;
		this.walletAddress = walletAddress;
	}

	public Double getWithdrawAmount() {
		return withdrawAmount;
	}

	public WithdrawalRequest setWithdrawAmount(Double withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
		return this;
	}

	public String getWalletAddress() {
		return walletAddress;
	}

	public WithdrawalRequest setWalletAddress(String walletAddress) {
		this.walletAddress = walletAddress;
		return this;
	}
}
