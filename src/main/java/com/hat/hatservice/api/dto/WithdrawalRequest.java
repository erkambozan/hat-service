package com.hat.hatservice.api.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class WithdrawalRequest {
	@JsonProperty("withdraw_amount")
	private Integer withdrawAmount;
	@JsonProperty("wallet_address")
	private String walletAddress;

	public WithdrawalRequest() {
	}

	public WithdrawalRequest(Integer withdrawAmount, String walletAddress) {
		this.withdrawAmount = withdrawAmount;
		this.walletAddress = walletAddress;
	}

	public Integer getWithdrawAmount() {
		return withdrawAmount;
	}

	public WithdrawalRequest setWithdrawAmount(Integer withdrawAmount) {
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
