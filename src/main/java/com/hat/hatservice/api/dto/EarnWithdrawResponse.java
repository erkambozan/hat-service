package com.hat.hatservice.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hat.hatservice.db.EarnWithdraw;

import java.util.UUID;

public class EarnWithdrawResponse {
	private UUID id;
	@JsonProperty("user_id")
	private UUID userId;
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

	public EarnWithdrawResponse() {
	}

	public EarnWithdrawResponse(EarnWithdraw earnWithdraw) {
		this.id = earnWithdraw.getId();
		this.userId = earnWithdraw.getUserId();
		this.coinType = earnWithdraw.getCoinType();
		this.coinPrice = earnWithdraw.getCoinPrice();
		this.withdrawAddress = earnWithdraw.getWithdrawAddress();
		this.withdrawAmount = earnWithdraw.getWithdrawAmount();
		this.status = earnWithdraw.getStatus();
	}

	public UUID getId() {
		return id;
	}

	public EarnWithdrawResponse setId(UUID id) {
		this.id = id;
		return this;
	}

	public UUID getUserId() {
		return userId;
	}

	public EarnWithdrawResponse setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public String getCoinType() {
		return coinType;
	}

	public EarnWithdrawResponse setCoinType(String coinType) {
		this.coinType = coinType;
		return this;
	}

	public Double getCoinPrice() {
		return coinPrice;
	}

	public EarnWithdrawResponse setCoinPrice(Double coinPrice) {
		this.coinPrice = coinPrice;
		return this;
	}

	public String getWithdrawAddress() {
		return withdrawAddress;
	}

	public EarnWithdrawResponse setWithdrawAddress(String withdrawAddress) {
		this.withdrawAddress = withdrawAddress;
		return this;
	}

	public Double getWithdrawAmount() {
		return withdrawAmount;
	}

	public EarnWithdrawResponse setWithdrawAmount(Double withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public EarnWithdrawResponse setStatus(String status) {
		this.status = status;
		return this;
	}
}
