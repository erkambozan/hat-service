package com.hat.hatservice.api.dto.payments;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentThreshold {
	OverpaymentAbsoluteThreshold overpayment_absolute_threshold;
	String overpayment_relative_threshold;
	UnderpaymentAbsoluteThreshold underpayment_absolute_threshold;
	String underpayment_relative_threshold;

	@JsonProperty("overpayment_absolute_threshold")
	public OverpaymentAbsoluteThreshold getOverpayment_absolute_threshold() {
		return this.overpayment_absolute_threshold;
	}

	public void setOverpayment_absolute_threshold(OverpaymentAbsoluteThreshold overpayment_absolute_threshold) {
		this.overpayment_absolute_threshold = overpayment_absolute_threshold;
	}

	@JsonProperty("overpayment_relative_threshold")
	public String getOverpayment_relative_threshold() {
		return this.overpayment_relative_threshold;
	}

	public void setOverpayment_relative_threshold(String overpayment_relative_threshold) {
		this.overpayment_relative_threshold = overpayment_relative_threshold;
	}

	@JsonProperty("underpayment_absolute_threshold")
	public UnderpaymentAbsoluteThreshold getUnderpayment_absolute_threshold() {
		return this.underpayment_absolute_threshold;
	}

	public void setUnderpayment_absolute_threshold(UnderpaymentAbsoluteThreshold underpayment_absolute_threshold) {
		this.underpayment_absolute_threshold = underpayment_absolute_threshold;
	}

	@JsonProperty("underpayment_relative_threshold")
	public String getUnderpayment_relative_threshold() {
		return this.underpayment_relative_threshold;
	}

	public void setUnderpayment_relative_threshold(String underpayment_relative_threshold) {
		this.underpayment_relative_threshold = underpayment_relative_threshold;
	}
}
