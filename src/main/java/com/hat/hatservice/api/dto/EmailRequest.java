package com.hat.hatservice.api.dto;

public class EmailRequest {
	private String title;
	private String description;

	public EmailRequest() {
	}

	public EmailRequest(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public EmailRequest setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public EmailRequest setDescription(String description) {
		this.description = description;
		return this;
	}
}
