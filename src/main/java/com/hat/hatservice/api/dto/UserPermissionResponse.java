package com.hat.hatservice.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public class UserPermissionResponse {
	@JsonProperty("user_id")
	private UUID userId;
	@JsonProperty("permissions_name")
	private List<String> permissionsName;

	public UserPermissionResponse() {
	}

	public UserPermissionResponse(UUID userId, List<String> permissionsName) {
		this.userId = userId;
		this.permissionsName = permissionsName;
	}

	public UUID getUserId() {
		return userId;
	}

	public UserPermissionResponse setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public List<String> getPermissionName() {
		return permissionsName;
	}

	public UserPermissionResponse setPermissionName(List<String> permissionName) {
		this.permissionsName = permissionName;
		return this;
	}
}
