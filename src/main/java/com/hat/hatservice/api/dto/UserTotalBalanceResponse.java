package com.hat.hatservice.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hat.hatservice.db.UserTotalBalance;

public class UserTotalBalanceResponse {
	@JsonProperty("withdrawable_balance")
	private Double withdrawableBalance;
	@JsonProperty("locked_balance")
	private Double lockedBalance;
	@JsonProperty("earn_balance")
	private Double earnBalance;

	public UserTotalBalanceResponse() {
	}

	public UserTotalBalanceResponse(UserTotalBalance userTotalBalance) {
		this.withdrawableBalance = userTotalBalance.getWithdrawableBalance();
		this.lockedBalance = userTotalBalance.getLockedBalance();
		this.earnBalance = userTotalBalance.getEarnBalance();
	}

	public Double getWithdrawableBalance() {
		return withdrawableBalance;
	}

	public UserTotalBalanceResponse setWithdrawableBalance(Double withdrawableBalance) {
		this.withdrawableBalance = withdrawableBalance;
		return this;
	}

	public Double getLockedBalance() {
		return lockedBalance;
	}

	public UserTotalBalanceResponse setLockedBalance(Double lockedBalance) {
		this.lockedBalance = lockedBalance;
		return this;
	}

	public Double getEarnBalance() {
		return earnBalance;
	}

	public UserTotalBalanceResponse setEarnBalance(Double earnBalance) {
		this.earnBalance = earnBalance;
		return this;
	}
}
