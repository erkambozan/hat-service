package com.hat.hatservice.db;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmailVerificationRepository extends CrudRepository<EmailVerification, UUID> {
	Optional<EmailVerification> findByUserId(UUID userId);
}
