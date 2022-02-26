package com.hat.hatservice.db;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface EarnWithdrawRepository extends CrudRepository<EarnWithdraw, UUID> {
	List<EarnWithdraw> findAllByUserId(UUID userId);
}
