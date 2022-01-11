package com.hat.hatservice.api.dto.payments;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Gross {
	Object local;
	Crypto crypto;

	@JsonProperty("local")
	public Object getLocal() {
		return this.local;
	}

	public void setLocal(Object local) {
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
