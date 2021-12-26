package com.hat.hatservice.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public class UserPermissionRequest {
	@JsonProperty("user_id")
	private UUID userId;
	@JsonProperty("permissions_name")
	private List<String> permissionsName;

	public UserPermissionRequest() {
	}

	public UserPermissionRequest(UUID userId, List<String> permissionsName) {
		this.userId = userId;
		this.permissionsName = permissionsName;
	}

	public UUID getUserId() {
		return userId;
	}

	public UserPermissionRequest setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public List<String> getPermissionsName() {
		return permissionsName;
	}

	public UserPermissionRequest setPermissionsName(List<String> permissionsName) {
		this.permissionsName = permissionsName;
		return this;
	}
}
