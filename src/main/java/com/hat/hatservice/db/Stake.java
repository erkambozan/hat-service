package com.hat.hatservice.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "stake")
public class Stake {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", columnDefinition = "uuid", unique = true)
	private UUID id;
	@Column(name = "userId")
	private UUID userId;
	@Column(name = "started_stake_amount")
	private Double startedStakeAmount;
	@Column(name = "expiry_stake_amount")
	private Double expiryStakeAmount;
	@Column(name = "expiry_stake_time")
	private Integer expiryStakeTime;
	@Column(name = "stake_percentage")
	private Double stakePercentage;
	@Column(name = "start_date", columnDefinition = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate = Date.from(Instant.now());
	@Column(name = "end_date", columnDefinition = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	@CreationTimestamp
	@Column(name = "created_at", columnDefinition = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt = Date.from(Instant.now());
	@UpdateTimestamp
	@Column(name = "updated_at", columnDefinition = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt = Date.from(Instant.now());

	public Stake() {
	}

	public Stake(UUID userId, Double startedStakeAmount, Double expiryStakeAmount, Integer expiryStakeTime, Double stakePercentage, Date endDate) {
		this.userId = userId;
		this.startedStakeAmount = startedStakeAmount;
		this.expiryStakeAmount = expiryStakeAmount;
		this.expiryStakeTime = expiryStakeTime;
		this.stakePercentage = stakePercentage;
		this.endDate = endDate;
	}

	public UUID getId() {
		return id;
	}

	public Stake setId(UUID id) {
		this.id = id;
		return this;
	}

	public UUID getUserId() {
		return userId;
	}

	public Stake setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public Double getStartedStakeAmount() {
		return startedStakeAmount;
	}

	public Stake setStartedStakeAmount(Double startedStakeAmount) {
		this.startedStakeAmount = startedStakeAmount;
		return this;
	}

	public Double getExpiryStakeAmount() {
		return expiryStakeAmount;
	}

	public Stake setExpiryStakeAmount(Double expiryStakeAmount) {
		this.expiryStakeAmount = expiryStakeAmount;
		return this;
	}

	public Integer getExpiryStakeTime() {
		return expiryStakeTime;
	}

	public Stake setExpiryStakeTime(Integer expiryStakeTime) {
		this.expiryStakeTime = expiryStakeTime;
		return this;
	}

	public Double getStakePercentage() {
		return stakePercentage;
	}

	public Stake setStakePercentage(Double stakePercentage) {
		this.stakePercentage = stakePercentage;
		return this;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Stake setStartDate(Date startDate) {
		this.startDate = startDate;
		return this;
	}

	public Date getEndDate() {
		return endDate;
	}

	public Stake setEndDate(Date endDate) {
		this.endDate = endDate;
		return this;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Stake setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public Stake setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
		return this;
	}
}
