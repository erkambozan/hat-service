package com.hat.hatservice.db;

import com.hat.hatservice.api.dto.WithdrawalRequest;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "withdrawal")
public class Withdrawal {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", columnDefinition = "uuid", unique = true)
	private UUID id;
	@Column(name = "user_id")
	private UUID userId;
	@Column(name = "withdraw_amount")
	private Double withdrawAmount;
	@Column(name = "wallet_address")
	private String walletAddress;
	@Column(name = "status")
	private String status;

	public Withdrawal() {
	}

	public Withdrawal(UUID userId, Double withdrawAmount, String walletAddress, String status) {
		this.userId = userId;
		this.withdrawAmount = withdrawAmount;
		this.walletAddress = walletAddress;
		this.status = status;
	}

	public void changeFields(WithdrawalRequest request){
		this.setWalletAddress(request.getWalletAddress());
		this.setWithdrawAmount(request.getWithdrawAmount());
	}

	public UUID getId() {
		return id;
	}

	public Withdrawal setId(UUID id) {
		this.id = id;
		return this;
	}

	public UUID getUserId() {
		return userId;
	}

	public Withdrawal setUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

	public Double getWithdrawAmount() {
		return withdrawAmount;
	}

	public Withdrawal setWithdrawAmount(Double withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
		return this;
	}

	public String getWalletAddress() {
		return walletAddress;
	}

	public Withdrawal setWalletAddress(String walletAddress) {
		this.walletAddress = walletAddress;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public Withdrawal setStatus(String status) {
		this.status = status;
		return this;
	}
}
