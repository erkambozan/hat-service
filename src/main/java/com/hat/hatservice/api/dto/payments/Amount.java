package com.hat.hatservice.api.dto.payments;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Amount {
	Gross gross;
	Net net;
	CoinbaseFee coinbase_fee;

	@JsonProperty("gross")
	public Gross getGross() {
		return this.gross;
	}

	public void setGross(Gross gross) {
		this.gross = gross;
	}

	@JsonProperty("net")
	public Net getNet() {
		return this.net;
	}

	public void setNet(Net net) {
		this.net = net;
	}

	@JsonProperty("coinbase_fee")
	public CoinbaseFee getCoinbase_fee() {
		return this.coinbase_fee;
	}

	public void setCoinbase_fee(CoinbaseFee coinbase_fee) {
		this.coinbase_fee = coinbase_fee;
	}
}
