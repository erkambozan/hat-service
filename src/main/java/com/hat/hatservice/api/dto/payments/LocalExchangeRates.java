package com.hat.hatservice.api.dto.payments;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocalExchangeRates {
	String bCHUSD;
	String bTCUSD;
	String eTHUSD;
	String lTCUSD;
	String dOGEUSD;
	String uSDCUSD;
	String dAIUSD;

	@JsonProperty("BCH-USD")
	public String getBCHUSD() {
		return this.bCHUSD;
	}

	public void setBCHUSD(String bCHUSD) {
		this.bCHUSD = bCHUSD;
	}

	@JsonProperty("BTC-USD")
	public String getBTCUSD() {
		return this.bTCUSD;
	}

	public void setBTCUSD(String bTCUSD) {
		this.bTCUSD = bTCUSD;
	}

	@JsonProperty("ETH-USD")
	public String getETHUSD() {
		return this.eTHUSD;
	}

	public void setETHUSD(String eTHUSD) {
		this.eTHUSD = eTHUSD;
	}

	@JsonProperty("LTC-USD")
	public String getLTCUSD() {
		return this.lTCUSD;
	}

	public void setLTCUSD(String lTCUSD) {
		this.lTCUSD = lTCUSD;
	}

	@JsonProperty("DOGE-USD")
	public String getDOGEUSD() {
		return this.dOGEUSD;
	}

	public void setDOGEUSD(String dOGEUSD) {
		this.dOGEUSD = dOGEUSD;
	}

	@JsonProperty("USDC-USD")
	public String getUSDCUSD() {
		return this.uSDCUSD;
	}

	public void setUSDCUSD(String uSDCUSD) {
		this.uSDCUSD = uSDCUSD;
	}

	@JsonProperty("DAI-USD")
	public String getDAIUSD() {
		return this.dAIUSD;
	}

	public void setDAIUSD(String dAIUSD) {
		this.dAIUSD = dAIUSD;
	}
}
