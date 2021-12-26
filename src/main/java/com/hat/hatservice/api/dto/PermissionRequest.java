package com.hat.hatservice.api.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class PermissionRequest {
	@JsonProperty("permission_name")
	private String permissionName;

	public PermissionRequest() {
	}

	public PermissionRequest(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public PermissionRequest setPermissionName(String permissionName) {
		this.permissionName = permissionName;
		return this;
	}
}
