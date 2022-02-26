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
@Table(name = "user_total_balance")
public class UserTotalBalance {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", columnDefinition = "uuid", unique = true)
	private UUID id;
	@Column(name = "user_id")
	private UUID userId;
	@Column(name = "withdrawable_balance")
	private Double withdrawableBalance;
	@Column(name = "locked_balance")
	private Double lockedBalance;
	@Column(name = "earn_balance")
	private Double earnBalance;
	@CreationTimestamp
	@Column(name = "created_at", columnDefinition = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt = Date.from(Instant.now());
	@UpdateTimestamp
	@Column(name = "updated_at", columnDefinition = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt = Date.from(Instant.now());

	public UserTotalBalance() {
	}

	public UserTotalBalance(UUID userId, Double withdrawableBalance, Double lockedBalance, Double earnBalance) {
		this.userId = userId;
		this.withdrawableBalance = withdrawableBalance;
		this.lockedBalance = lockedBalance;
		this.earnBalance = earnBalance;
	}

	public UUID getId() {
		return id;
	}

	public UserTotalBalance setId(UUID id) {
		this.id = id;
		return this;
	}

	public UUID getUserId() {
		return userId;
	}

	public UserTotalBalance setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public Double getWithdrawableBalance() {
		return withdrawableBalance;
	}

	public UserTotalBalance setWithdrawableBalance(Double withdrawableBalance) {
		this.withdrawableBalance = withdrawableBalance;
		return this;
	}

	public Double getLockedBalance() {
		return lockedBalance;
	}

	public UserTotalBalance setLockedBalance(Double locked_balance) {
		this.lockedBalance = locked_balance;
		return this;
	}

	public Double getEarnBalance() {
		return earnBalance;
	}

	public UserTotalBalance setEarnBalance(Double earnBalance) {
		this.earnBalance = earnBalance;
		return this;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public UserTotalBalance setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public UserTotalBalance setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
		return this;
	}
}
