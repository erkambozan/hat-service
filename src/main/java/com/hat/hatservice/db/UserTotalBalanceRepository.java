package com.hat.hatservice.db;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserTotalBalanceRepository extends CrudRepository<UserTotalBalance, UUID> {
	Optional<UserTotalBalance> findByUserId(UUID userId);
}
