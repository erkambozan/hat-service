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
@Table(name = "stake_settings")
public class StakeSettings {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", columnDefinition = "uuid", unique = true)
	private UUID id;
	@Column(name = "expiry_stake_time")
	private int expiryStakeTime;
	@Column(name = "stake_percentage")
	private Double stakePercentage;
	@Column(name = "stake_type")
	private String stakeType;
	@Column(name = "minimum_limit")
	private Double minimumLimit;
	@CreationTimestamp
	@Column(name = "created_at", columnDefinition = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt = Date.from(Instant.now());
	@UpdateTimestamp
	@Column(name = "updated_at", columnDefinition = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt = Date.from(Instant.now());

	public StakeSettings() {
	}

	public StakeSettings(int expiryStakeTime, Double stakePercentage, String stakeType, Double minimumLimit) {
		this.expiryStakeTime = expiryStakeTime;
		this.stakePercentage = stakePercentage;
		this.stakeType = stakeType;
		this.minimumLimit = minimumLimit;
	}

	public UUID getId() {
		return id;
	}

	public StakeSettings setId(UUID id) {
		this.id = id;
		return this;
	}

	public int getExpiryStakeTime() {
		return expiryStakeTime;
	}

	public StakeSettings setExpiryStakeTime(int expiryStakeTime) {
		this.expiryStakeTime = expiryStakeTime;
		return this;
	}

	public Double getStakePercentage() {
		return stakePercentage;
	}

	public StakeSettings setStakePercentage(Double stakePercentage) {
		this.stakePercentage = stakePercentage;
		return this;
	}

	public String getStakeType() {
		return stakeType;
	}

	public StakeSettings setStakeType(String stakeType) {
		this.stakeType = stakeType;
		return this;
	}

	public Double getMinimumLimit() {
		return minimumLimit;
	}

	public StakeSettings setMinimumLimit(Double minimumLimit) {
		this.minimumLimit = minimumLimit;
		return this;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public StakeSettings setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public StakeSettings setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
		return this;
	}
}
