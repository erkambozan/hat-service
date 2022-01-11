package com.hat.hatservice.api.dto.payments;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Addresses {
	String ethereum;
	String usdc;
	String bitcoincash;
	String dogecoin;
	String litecoin;
	String bitcoin;
	String dai;

	@JsonProperty("ethereum")
	public String getEthereum() {
		return this.ethereum;
	}

	public void setEthereum(String ethereum) {
		this.ethereum = ethereum;
	}

	@JsonProperty("usdc")
	public String getUsdc() {
		return this.usdc;
	}

	public void setUsdc(String usdc) {
		this.usdc = usdc;
	}

	@JsonProperty("bitcoincash")
	public String getBitcoincash() {
		return this.bitcoincash;
	}

	public void setBitcoincash(String bitcoincash) {
		this.bitcoincash = bitcoincash;
	}

	@JsonProperty("dogecoin")
	public String getDogecoin() {
		return this.dogecoin;
	}

	public void setDogecoin(String dogecoin) {
		this.dogecoin = dogecoin;
	}

	@JsonProperty("litecoin")
	public String getLitecoin() {
		return this.litecoin;
	}

	public void setLitecoin(String litecoin) {
		this.litecoin = litecoin;
	}

	@JsonProperty("bitcoin")
	public String getBitcoin() {
		return this.bitcoin;
	}

	public void setBitcoin(String bitcoin) {
		this.bitcoin = bitcoin;
	}

	@JsonProperty("dai")
	public String getDai() {
		return this.dai;
	}

	public void setDai(String dai) {
		this.dai = dai;
	}
}
