package com.hat.hatservice.api.dto.payments;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Root {
	Pagination pagination;
	ArrayList<Datum> data;
	ArrayList<String> warnings;

	@JsonProperty("pagination")
	public Pagination getPagination() {
		return this.pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	@JsonProperty("data")
	public ArrayList<Datum> getData() {
		return this.data;
	}

	public void setData(ArrayList<Datum> data) {
		this.data = data;
	}

	@JsonProperty("warnings")
	public ArrayList<String> getWarnings() {
		return this.warnings;
	}

	public void setWarnings(ArrayList<String> warnings) {
		this.warnings = warnings;
	}
}
