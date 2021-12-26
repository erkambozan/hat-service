package com.hat.hatservice.db;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface WithdrawalRepository extends CrudRepository<Withdrawal, UUID> {
	List<Withdrawal> findAllByUserId(UUID userId);
}
