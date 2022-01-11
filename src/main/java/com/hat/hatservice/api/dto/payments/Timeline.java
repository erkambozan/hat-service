package com.hat.hatservice.api.dto.payments;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Timeline {
	String status;
	Date time;
	Payment payment;

	@JsonProperty("status")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("time")
	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@JsonProperty("payment")
	public Payment getPayment() {
		return this.payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
}
