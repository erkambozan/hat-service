package com.hat.hatservice.db;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionsRepository extends CrudRepository<Transactions, UUID> {
	List<Transactions> findAllByUserId(UUID userId);

	void deleteByWithdrawId(UUID withdrawId);
}
