package com.hat.hatservice.db;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface StakeRepository extends CrudRepository<Stake, UUID> {
	List<Stake> findAllByEndDateAndStakeStatus(Date endDate, Boolean stakeStatus);
	List<Stake> findAllByUserId(UUID userId);
}
