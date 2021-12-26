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
@Table(name = "permission")
public class Permission {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", columnDefinition = "uuid", unique = true)
	private UUID id;

	@Column(name = "permission_name")
	private String name;

	@CreationTimestamp
	@Column(name = "created_at", columnDefinition = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt = Date.from(Instant.now());

	@UpdateTimestamp
	@Column(name = "updated_at", columnDefinition = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt = Date.from(Instant.now());

	public Permission() {
	}

	public Permission(String name) {
		this.name = name;
	}

	public UUID getId() {
		return id;
	}

	public Permission setId(UUID id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Permission setName(String name) {
		this.name = name;
		return this;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Permission setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public Permission setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
		return this;
	}
}
