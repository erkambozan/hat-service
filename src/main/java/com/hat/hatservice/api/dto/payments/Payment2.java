package com.hat.hatservice.api.dto.payments;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Payment2 {
	String network;
	String transaction_id;
	Value value;

	@JsonProperty("network")
	public String getNetwork() {
		return this.network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	@JsonProperty("transaction_id")
	public String getTransaction_id() {
		return this.transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	@JsonProperty("value")
	public Value getValue() {
		return this.value;
	}

	public void setValue(Value value) {
		this.value = value;
	}
}
