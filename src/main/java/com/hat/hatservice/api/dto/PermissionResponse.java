package com.hat.hatservice.api.dto;

import java.util.UUID;

public class PermissionResponse {
	private UUID id;
	private String permissionName;

	public PermissionResponse() {
	}

	public PermissionResponse(UUID id, String permissionName) {
		this.id = id;
		this.permissionName = permissionName;
	}

	public UUID getId() {
		return id;
	}

	public PermissionResponse setId(UUID id) {
		this.id = id;
		return this;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public PermissionResponse setPermissionName(String permissionName) {
		this.permissionName = permissionName;
		return this;
	}
}
