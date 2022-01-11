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
@Table(name = "payment")
public class Payment {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", columnDefinition = "uuid", unique = true)
	private UUID id;

	@Column(name = "user_id")
	private UUID userId;

	@Column(name = "user_email")
	private String userEmail;

	@Column(name = "transaction_id")
	private String transactionId;

	@Column(name = "usd_amount")
	private Double usdAmount;

	@Column(name = "token_amount")
	private Double tokenAmount;

	@Column(name = "currency")
	private String currency;

	@Column(name = "currency_amount")
	private Double currencyAmount;

	@CreationTimestamp
	@Column(name = "created_at", columnDefinition = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt = Date.from(Instant.now());

	@UpdateTimestamp
	@Column(name = "updated_at", columnDefinition = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt = Date.from(Instant.now());

	public Payment() {
	}

	public Payment(UUID userId, String userEmail, String transactionId, Double usdAmount, Double tokenAmount, String currency, Double currencyAmount) {
		this.userId = userId;
		this.userEmail = userEmail;
		this.transactionId = transactionId;
		this.usdAmount = usdAmount;
		this.tokenAmount = tokenAmount;
		this.currency = currency;
		this.currencyAmount = currencyAmount;
	}

	public UUID getId() {
		return id;
	}

	public Payment setId(UUID id) {
		this.id = id;
		return this;
	}

	public UUID getUserId() {
		return userId;
	}

	public Payment setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public Payment setUserEmail(String userEmail) {
		this.userEmail = userEmail;
		return this;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public Payment setTransactionId(String transactionId) {
		this.transactionId = transactionId;
		return this;
	}

	public Double getUsdAmount() {
		return usdAmount;
	}

	public Payment setUsdAmount(Double usdAmount) {
		this.usdAmount = usdAmount;
		return this;
	}

	public Double getTokenAmount() {
		return tokenAmount;
	}

	public Payment setTokenAmount(Double tokenAmount) {
		this.tokenAmount = tokenAmount;
		return this;
	}

	public String getCurrency() {
		return currency;
	}

	public Payment setCurrency(String currency) {
		this.currency = currency;
		return this;
	}

	public Double getCurrencyAmount() {
		return currencyAmount;
	}

	public Payment setCurrencyAmount(Double currencyAmount) {
		this.currencyAmount = currencyAmount;
		return this;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Payment setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public Payment setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
		return this;
	}
}
