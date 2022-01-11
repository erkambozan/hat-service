package com.hat.hatservice.api.dto.payments;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinbaseProcessingFee {
	Local local;
	Crypto crypto;

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
}
