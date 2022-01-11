package com.hat.hatservice.api.dto.payments;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Deposited {
	boolean autoconversion_enabled;
	String autoconversion_status;
	String status;
	String destination;
	Object exchange_rate;
	Amount amount;

	@JsonProperty("autoconversion_enabled")
	public boolean getAutoconversion_enabled() {
		return this.autoconversion_enabled;
	}

	public void setAutoconversion_enabled(boolean autoconversion_enabled) {
		this.autoconversion_enabled = autoconversion_enabled;
	}

	@JsonProperty("autoconversion_status")
	public String getAutoconversion_status() {
		return this.autoconversion_status;
	}

	public void setAutoconversion_status(String autoconversion_status) {
		this.autoconversion_status = autoconversion_status;
	}

	@JsonProperty("status")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("destination")
	public String getDestination() {
		return this.destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@JsonProperty("exchange_rate")
	public Object getExchange_rate() {
		return this.exchange_rate;
	}

	public void setExchange_rate(Object exchange_rate) {
		this.exchange_rate = exchange_rate;
	}

	@JsonProperty("amount")
	public Amount getAmount() {
		return this.amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Deposited{" +
				"autoconversion_enabled=" + autoconversion_enabled +
				", autoconversion_status='" + autoconversion_status + '\'' +
				", status='" + status + '\'' +
				", destination='" + destination + '\'' +
				", exchange_rate=" + exchange_rate +
				", amount=" + amount +
				'}';
	}
}
