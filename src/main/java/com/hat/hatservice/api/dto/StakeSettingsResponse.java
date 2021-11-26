package com.hat.hatservice.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class StakeSettingsResponse {
	private UUID id;
	@JsonProperty("expiry_stake_time")
	private int expiryStakeTime;
	@JsonProperty("stake_percentage")
	private Double stakePercentage;
	@JsonProperty("stake_type")
	private String stakeType;
	@JsonProperty("minimum_limit")
	private Double minimumLimit;

	public StakeSettingsResponse() {
	}

	public StakeSettingsResponse(UUID id, int expiryStakeTime, Double stakePercentage, String stakeType, Double minimumLimit) {
		this.id = id;
		this.expiryStakeTime = expiryStakeTime;
		this.stakePercentage = stakePercentage;
		this.stakeType = stakeType;
		this.minimumLimit = minimumLimit;
	}

	public UUID getId() {
		return id;
	}

	public StakeSettingsResponse setId(UUID id) {
		this.id = id;
		return this;
	}

	public int getExpiryStakeTime() {
		return expiryStakeTime;
	}

	public StakeSettingsResponse setExpiryStakeTime(int expiryStakeTime) {
		this.expiryStakeTime = expiryStakeTime;
		return this;
	}

	public Double getStakePercentage() {
		return stakePercentage;
	}

	public StakeSettingsResponse setStakePercentage(Double stakePercentage) {
		this.stakePercentage = stakePercentage;
		return this;
	}

	public String getStakeType() {
		return stakeType;
	}

	public StakeSettingsResponse setStakeType(String stakeType) {
		this.stakeType = stakeType;
		return this;
	}

	public Double getMinimumLimit() {
		return minimumLimit;
	}

	public StakeSettingsResponse setMinimumLimit(Double minimumLimit) {
		this.minimumLimit = minimumLimit;
		return this;
	}
}
