package com.hat.hatservice.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hat.hatservice.db.Transactions;

import java.util.Date;
import java.util.UUID;

public class TransactionsResponse {
	private UUID id;
	@JsonProperty("user_id")
	private UUID userId;
	@JsonProperty("amount")
	private Double amount;
	@JsonProperty("title")
	private String title;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private java.util.Date transactionDate;

	public TransactionsResponse() {
	}

	public TransactionsResponse(Transactions transactions) {
		this.id = transactions.getId();
		this.userId = transactions.getUserId();
		this.amount = transactions.getAmount();
		this.title = transactions.getTitle();
		this.transactionDate = transactions.getCreatedAt();
	}

	public UUID getId() {
		return id;
	}

	public TransactionsResponse setId(UUID id) {
		this.id = id;
		return this;
	}

	public UUID getUserId() {
		return userId;
	}

	public TransactionsResponse setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public Double getAmount() {
		return amount;
	}

	public TransactionsResponse setAmount(Double amount) {
		this.amount = amount;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public TransactionsResponse setTitle(String title) {
		this.title = title;
		return this;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public TransactionsResponse setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
		return this;
	}
}
