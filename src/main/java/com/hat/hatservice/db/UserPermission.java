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
@Table(name = "user_permission")
public class UserPermission {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", columnDefinition = "uuid", unique = true)
	private UUID id;

	@Column(name = "user_id")
	private UUID userId;

	@Column(name = "permission_id")
	private UUID permissionId;

	@CreationTimestamp
	@Column(name = "created_at", columnDefinition = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt = Date.from(Instant.now());

	@UpdateTimestamp
	@Column(name = "updated_at", columnDefinition = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt = Date.from(Instant.now());

	public UserPermission() {
	}

	public UserPermission(UUID userId, UUID permissionId) {
		this.userId = userId;
		this.permissionId = permissionId;
	}

	public UUID getId() {
		return id;
	}

	public UserPermission setId(UUID id) {
		this.id = id;
		return this;
	}

	public UUID getUserId() {
		return userId;
	}

	public UserPermission setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public UUID getPermissionId() {
		return permissionId;
	}

	public UserPermission setPermissionId(UUID permissionId) {
		this.permissionId = permissionId;
		return this;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public UserPermission setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public UserPermission setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
		return this;
	}
}

