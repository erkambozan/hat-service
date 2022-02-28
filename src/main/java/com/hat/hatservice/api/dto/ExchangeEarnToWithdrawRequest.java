package com.hat.hatservice.api.dto;

public class ExchangeEarnToWithdrawRequest {
	private Integer amount;

	public ExchangeEarnToWithdrawRequest() {
	}

	public ExchangeEarnToWithdrawRequest(Integer amount) {
		this.amount = amount;
	}

	public Integer getAmount() {
		return amount;
	}

	public ExchangeEarnToWithdrawRequest setAmount(Integer amount) {
		this.amount = amount;
		return this;
	}
}
