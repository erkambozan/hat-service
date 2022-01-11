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
@Table(name = "transactions")
public class Transactions {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", columnDefinition = "uuid", unique = true)
	private UUID id;

	@Column(name = "user_id")
	private UUID userId;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "title")
	private String title;

	@CreationTimestamp
	@Column(name = "created_at", columnDefinition = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt = Date.from(Instant.now());
	@UpdateTimestamp
	@Column(name = "updated_at", columnDefinition = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt = Date.from(Instant.now());

	public Transactions() {
	}

	public Transactions(UUID userId, Double amount, String title) {
		this.userId = userId;
		this.amount = amount;
		this.title = title;
	}

	public UUID getId() {
		return id;
	}

	public Transactions setId(UUID id) {
		this.id = id;
		return this;
	}

	public UUID getUserId() {
		return userId;
	}

	public Transactions setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public Double getAmount() {
		return amount;
	}

	public Transactions setAmount(Double amount) {
		this.amount = amount;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Transactions setTitle(String title) {
		this.title = title;
		return this;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Transactions setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public Transactions setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
		return this;
	}
}
