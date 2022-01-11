package com.hat.hatservice.api.dto.payments;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pricing {
	Local local;
	Ethereum ethereum;
	Usdc usdc;
	Bitcoincash bitcoincash;
	Dogecoin dogecoin;
	Litecoin litecoin;
	Bitcoin bitcoin;
	Dai dai;

	@JsonProperty("local")
	public Local getLocal() {
		return this.local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	@JsonProperty("ethereum")
	public Ethereum getEthereum() {
		return this.ethereum;
	}

	public void setEthereum(Ethereum ethereum) {
		this.ethereum = ethereum;
	}

	@JsonProperty("usdc")
	public Usdc getUsdc() {
		return this.usdc;
	}

	public void setUsdc(Usdc usdc) {
		this.usdc = usdc;
	}

	@JsonProperty("bitcoincash")
	public Bitcoincash getBitcoincash() {
		return this.bitcoincash;
	}

	public void setBitcoincash(Bitcoincash bitcoincash) {
		this.bitcoincash = bitcoincash;
	}

	@JsonProperty("dogecoin")
	public Dogecoin getDogecoin() {
		return this.dogecoin;
	}

	public void setDogecoin(Dogecoin dogecoin) {
		this.dogecoin = dogecoin;
	}

	@JsonProperty("litecoin")
	public Litecoin getLitecoin() {
		return this.litecoin;
	}

	public void setLitecoin(Litecoin litecoin) {
		this.litecoin = litecoin;
	}

	@JsonProperty("bitcoin")
	public Bitcoin getBitcoin() {
		return this.bitcoin;
	}

	public void setBitcoin(Bitcoin bitcoin) {
		this.bitcoin = bitcoin;
	}

	@JsonProperty("dai")
	public Dai getDai() {
		return this.dai;
	}

	public void setDai(Dai dai) {
		this.dai = dai;
	}
}
