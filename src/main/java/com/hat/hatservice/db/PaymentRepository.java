package com.hat.hatservice.db;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends CrudRepository<Payment, UUID> {
	Optional<Payment> findByTransactionId(String transactionId);
}
