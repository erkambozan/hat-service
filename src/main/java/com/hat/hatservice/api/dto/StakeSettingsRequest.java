package com.hat.hatservice.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class StakeSettingsRequest {
	@JsonProperty("expiry_stake_time")
	private int expiryStakeTime;
	@JsonProperty("stake_percentage")
	private Double stakePercentage;
	@JsonProperty("stake_type")
	private String stakeType;
	@JsonProperty("minimum_limit")
	private Double minimumLimit;
	@JsonProperty("maximum_limit")
	private Double maximumLimit;

	public StakeSettingsRequest() {
	}

	public StakeSettingsRequest(int expiryStakeTime, Double stakePercentage, String stakeType, Double minimumLimit, Double maximumLimit) {
		this.expiryStakeTime = expiryStakeTime;
		this.stakePercentage = stakePercentage;
		this.stakeType = stakeType;
		this.minimumLimit = minimumLimit;
		this.maximumLimit = maximumLimit;
	}

	public int getExpiryStakeTime() {
		return expiryStakeTime;
	}

	public StakeSettingsRequest setExpiryStakeTime(int expiryStakeTime) {
		this.expiryStakeTime = expiryStakeTime;
		return this;
	}

	public Double getStakePercentage() {
		return stakePercentage;
	}

	public StakeSettingsRequest setStakePercentage(Double stakePercentage) {
		this.stakePercentage = stakePercentage;
		return this;
	}

	public String getStakeType() {
		return stakeType;
	}

	public StakeSettingsRequest setStakeType(String stakeType) {
		this.stakeType = stakeType;
		return this;
	}

	public Double getMinimumLimit() {
		return minimumLimit;
	}

	public StakeSettingsRequest setMinimumLimit(Double minimumLimit) {
		this.minimumLimit = minimumLimit;
		return this;
	}

	public Double getMaximumLimit() {
		return maximumLimit;
	}

	public StakeSettingsRequest setMaximumLimit(Double maximumLimit) {
		this.maximumLimit = maximumLimit;
		return this;
	}
}
