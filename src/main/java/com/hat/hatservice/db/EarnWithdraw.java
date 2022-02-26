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
@Table(name = "earn_withdraw")
public class EarnWithdraw {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID id;
	@Column(name = "user_id")
	private UUID userId;
	@Column(name = "coin_type")
	private String coinType;
	@Column(name = "coin_price")
	private Double coinPrice;
	@Column(name = "withdraw_address")
	private String withdrawAddress;
	@Column(name = "withdrawAmount")
	private Double withdrawAmount;
	@Column(name = "status")
	private String status;
	@CreationTimestamp
	@Column(name = "created_at", columnDefinition = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt = Date.from(Instant.now());
	@UpdateTimestamp
	@Column(name = "updated_at", columnDefinition = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt = Date.from(Instant.now());

	public EarnWithdraw() {
	}

	public EarnWithdraw(UUID userId, String coinType, Double coinPrice, String withdrawAddress, Double withdrawAmount, String status) {
		this.userId = userId;
		this.coinType = coinType;
		this.coinPrice = coinPrice;
		this.withdrawAddress = withdrawAddress;
		this.withdrawAmount = withdrawAmount;
		this.status = status;
	}

	public UUID getId() {
		return id;
	}

	public EarnWithdraw setId(UUID id) {
		this.id = id;
		return this;
	}

	public UUID getUserId() {
		return userId;
	}

	public EarnWithdraw setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public String getCoinType() {
		return coinType;
	}

	public EarnWithdraw setCoinType(String coinType) {
		this.coinType = coinType;
		return this;
	}

	public Double getCoinPrice() {
		return coinPrice;
	}

	public EarnWithdraw setCoinPrice(Double coinPrice) {
		this.coinPrice = coinPrice;
		return this;
	}

	public String getWithdrawAddress() {
		return withdrawAddress;
	}

	public EarnWithdraw setWithdrawAddress(String withdrawAddress) {
		this.withdrawAddress = withdrawAddress;
		return this;
	}

	public Double getWithdrawAmount() {
		return withdrawAmount;
	}

	public EarnWithdraw setWithdrawAmount(Double withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public EarnWithdraw setStatus(String status) {
		this.status = status;
		return this;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public EarnWithdraw setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public EarnWithdraw setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
		return this;
	}
}
