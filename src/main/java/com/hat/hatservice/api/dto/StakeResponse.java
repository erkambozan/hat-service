package com.hat.hatservice.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hat.hatservice.db.Stake;

import java.util.Date;
import java.util.UUID;

public class StakeResponse {
	private UUID id;
	@JsonProperty("user_id")
	private UUID userId;
	@JsonProperty("started_stake_amount")
	private Double startedStakeAmount;
	@JsonProperty("expiry_stake_amount")
	private Double expiryStakeAmount;
	@JsonProperty("expiry_stake_time")
	private Integer expiryStakeTime;
	@JsonProperty("stake_percentage")
	private Double stakePercentage;
	@JsonProperty("stake_type")
	private String stakeType;
	@JsonProperty("stake_status")
	private Boolean stakeStatus;
	@JsonProperty("start_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	@JsonProperty("end_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endDate;

	public StakeResponse() {
	}

	public StakeResponse(Stake stake){
		this.id = stake.getId();
		this.userId = stake.getUserId();
		this.startedStakeAmount = stake.getStartedStakeAmount();
		this.expiryStakeAmount = stake.getExpiryStakeAmount();
		this.expiryStakeTime = stake.getExpiryStakeTime();
		this.stakePercentage = stake.getStakePercentage();
		this.stakeType = stake.getStakeType();
		this.stakeStatus = stake.getStakeStatus();
		this.startDate = stake.getStartDate();
		this.endDate = stake.getEndDate();
	}

	public StakeResponse(UUID id, UUID userId, Double startedStakeAmount, Double expiryStakeAmount, Integer expiryStakeTime, Double stakePercentage, String stakeType, Boolean stakeStatus, Date startDate, Date endDate) {
		this.id = id;
		this.userId = userId;
		this.startedStakeAmount = startedStakeAmount;
		this.expiryStakeAmount = expiryStakeAmount;
		this.expiryStakeTime = expiryStakeTime;
		this.stakePercentage = stakePercentage;
		this.stakeType = stakeType;
		this.stakeStatus = stakeStatus;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public UUID getId() {
		return id;
	}

	public StakeResponse setId(UUID id) {
		this.id = id;
		return this;
	}

	public UUID getUserId() {
		return userId;
	}

	public StakeResponse setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public Double getStartedStakeAmount() {
		return startedStakeAmount;
	}

	public StakeResponse setStartedStakeAmount(Double startedStakeAmount) {
		this.startedStakeAmount = startedStakeAmount;
		return this;
	}

	public Double getExpiryStakeAmount() {
		return expiryStakeAmount;
	}

	public StakeResponse setExpiryStakeAmount(Double expiryStakeAmount) {
		this.expiryStakeAmount = expiryStakeAmount;
		return this;
	}

	public Integer getExpiryStakeTime() {
		return expiryStakeTime;
	}

	public StakeResponse setExpiryStakeTime(Integer expiryStakeTime) {
		this.expiryStakeTime = expiryStakeTime;
		return this;
	}

	public Double getStakePercentage() {
		return stakePercentage;
	}

	public StakeResponse setStakePercentage(Double stakePercentage) {
		this.stakePercentage = stakePercentage;
		return this;
	}

	public String getStakeType() {
		return stakeType;
	}

	public StakeResponse setStakeType(String stakeType) {
		this.stakeType = stakeType;
		return this;
	}

	public Boolean getStakeStatus() {
		return stakeStatus;
	}

	public StakeResponse setStakeStatus(Boolean stakeStatus) {
		this.stakeStatus = stakeStatus;
		return this;
	}

	public Date getStartDate() {
		return startDate;
	}

	public StakeResponse setStartDate(Date startDate) {
		this.startDate = startDate;
		return this;
	}

	public Date getEndDate() {
		return endDate;
	}

	public StakeResponse setEndDate(Date endDate) {
		this.endDate = endDate;
		return this;
	}
}
