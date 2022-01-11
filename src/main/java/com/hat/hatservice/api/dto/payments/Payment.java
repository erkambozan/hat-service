package com.hat.hatservice.api.dto.payments;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Payment {
	String network;
	String transaction_id;
	String status;
	Date detected_at;
	Value value;
	Block block;
	Deposited deposited;
	CoinbaseProcessingFee coinbase_processing_fee;
	Net net;

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

	@JsonProperty("status")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("detected_at")
	public Date getDetected_at() {
		return this.detected_at;
	}

	public void setDetected_at(Date detected_at) {
		this.detected_at = detected_at;
	}

	@JsonProperty("value")
	public Value getValue() {
		return this.value;
	}

	public void setValue(Value value) {
		this.value = value;
	}

	@JsonProperty("block")
	public Block getBlock() {
		return this.block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	@JsonProperty("deposited")
	public Deposited getDeposited() {
		return this.deposited;
	}

	public void setDeposited(Deposited deposited) {
		this.deposited = deposited;
	}

	@JsonProperty("coinbase_processing_fee")
	public CoinbaseProcessingFee getCoinbase_processing_fee() {
		return this.coinbase_processing_fee;
	}

	public void setCoinbase_processing_fee(CoinbaseProcessingFee coinbase_processing_fee) {
		this.coinbase_processing_fee = coinbase_processing_fee;
	}

	@JsonProperty("net")
	public Net getNet() {
		return this.net;
	}

	public void setNet(Net net) {
		this.net = net;
	}
}
