package com.hat.hatservice.api.dto.payments;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
public class Pagination {
	String order;
	Object starting_after;
	Object ending_before;
	int total;
	int limit;
	int yielded;
	ArrayList<String> cursor_range;
	String previous_uri;
	String next_uri;

	@JsonProperty("order")
	public String getOrder() {
		return this.order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	@JsonProperty("starting_after")
	public Object getStarting_after() {
		return this.starting_after;
	}

	public void setStarting_after(Object starting_after) {
		this.starting_after = starting_after;
	}

	@JsonProperty("ending_before")
	public Object getEnding_before() {
		return this.ending_before;
	}

	public void setEnding_before(Object ending_before) {
		this.ending_before = ending_before;
	}

	@JsonProperty("total")
	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@JsonProperty("limit")
	public int getLimit() {
		return this.limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	@JsonProperty("yielded")
	public int getYielded() {
		return this.yielded;
	}

	public void setYielded(int yielded) {
		this.yielded = yielded;
	}

	@JsonProperty("cursor_range")
	public ArrayList<String> getCursor_range() {
		return this.cursor_range;
	}

	public void setCursor_range(ArrayList<String> cursor_range) {
		this.cursor_range = cursor_range;
	}

	@JsonProperty("previous_uri")
	public String getPrevious_uri() {
		return this.previous_uri;
	}

	public void setPrevious_uri(String previous_uri) {
		this.previous_uri = previous_uri;
	}

	@JsonProperty("next_uri")
	public String getNext_uri() {
		return this.next_uri;
	}

	public void setNext_uri(String next_uri) {
		this.next_uri = next_uri;
	}
}


