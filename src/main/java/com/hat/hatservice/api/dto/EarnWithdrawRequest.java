package com.hat.hatservice.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EarnWithdrawRequest {
	@JsonProperty("coin_type")
	private String coinType;
	@JsonProperty("coin_price")
	private Double coinPrice;
	@JsonProperty("withdraw_address")
	private String withdrawAddress;
	@JsonProperty("withdraw_amount")
	private Double withdrawAmount;
	@JsonProperty("status")
	private String status;

	public EarnWithdrawRequest() {
	}

	public EarnWithdrawRequest(String coinType, Double coinPrice, String withdrawAddress, Double withdrawAmount) {
		this.coinType = coinType;
		this.coinPrice = coinPrice;
		this.withdrawAddress = withdrawAddress;
		this.withdrawAmount = withdrawAmount;
	}

	public EarnWithdrawRequest(String coinType, Double coinPrice, String withdrawAddress, Double withdrawAmount, String status) {
		this.coinType = coinType;
		this.coinPrice = coinPrice;
		this.withdrawAddress = withdrawAddress;
		this.withdrawAmount = withdrawAmount;
		this.status = status;
	}

	public String getCoinType() {
		return coinType;
	}

	public EarnWithdrawRequest setCoinType(String coinType) {
		this.coinType = coinType;
		return this;
	}

	public Double getCoinPrice() {
		return coinPrice;
	}

	public EarnWithdrawRequest setCoinPrice(Double coinPrice) {
		this.coinPrice = coinPrice;
		return this;
	}

	public String getWithdrawAddress() {
		return withdrawAddress;
	}

	public EarnWithdrawRequest setWithdrawAddress(String withdrawAddress) {
		this.withdrawAddress = withdrawAddress;
		return this;
	}

	public Double getWithdrawAmount() {
		return withdrawAmount;
	}

	public EarnWithdrawRequest setWithdrawAmount(Double withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public EarnWithdrawRequest setStatus(String status) {
		this.status = status;
		return this;
	}
}
