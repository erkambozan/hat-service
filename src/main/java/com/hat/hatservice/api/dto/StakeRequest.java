package com.hat.hatservice.api.dto;

public class StakeRequest {
	private String stakeType;
	private Double stakeAmount;

	public StakeRequest() {
	}

	public StakeRequest(String stakeType, Double stakeAmount) {
		this.stakeType = stakeType;
		this.stakeAmount = stakeAmount;
	}

	public String getStakeType() {
		return stakeType;
	}

	public StakeRequest setStakeType(String stakeType) {
		this.stakeType = stakeType;
		return this;
	}

	public Double getStakeAmount() {
		return stakeAmount;
	}

	public StakeRequest setStakeAmount(Double stakeAmount) {
		this.stakeAmount = stakeAmount;
		return this;
	}
}

