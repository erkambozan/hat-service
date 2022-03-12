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
@Table(name = "email_verification")
public class EmailVerification {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID id;
	@Column(name = "user_id")
	private UUID userId;
	@Column(name = "verification_code")
	private Integer verificationCode;
	@Column(name = "end_time")
	private Date endTime;
	@CreationTimestamp
	@Column(name = "created_at", columnDefinition = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt = Date.from(Instant.now());
	@UpdateTimestamp
	@Column(name = "updated_at", columnDefinition = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt = Date.from(Instant.now());

	public EmailVerification() {
	}

	public EmailVerification(UUID userId) {
		this.userId = userId;
	}

	public UUID getId() {
		return id;
	}

	public EmailVerification setId(UUID id) {
		this.id = id;
		return this;
	}

	public UUID getUserId() {
		return userId;
	}

	public EmailVerification setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public Integer getVerificationCode() {
		return verificationCode;
	}

	public EmailVerification setVerificationCode(Integer verificationCode) {
		this.verificationCode = verificationCode;
		return this;
	}

	public Date getEndTime() {
		return endTime;
	}

	public EmailVerification setEndTime(Date endTime) {
		this.endTime = endTime;
		return this;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public EmailVerification setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public EmailVerification setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
		return this;
	}
}
