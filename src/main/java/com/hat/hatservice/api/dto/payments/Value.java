package com.hat.hatservice.api.dto.payments;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Value {
	Local local;
	Crypto crypto;
	String amount;
	String currency;

	@JsonProperty("local")
	public Local getLocal() {
		return this.local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	@JsonProperty("crypto")
	public Crypto getCrypto() {
		return this.crypto;
	}

	public void setCrypto(Crypto crypto) {
		this.crypto = crypto;
	}

	@JsonProperty("amount")
	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	@JsonProperty("currency")
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
