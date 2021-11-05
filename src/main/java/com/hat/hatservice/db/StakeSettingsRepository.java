package com.hat.hatservice.db;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface StakeSettingsRepository extends CrudRepository<StakeSettings, UUID> {
	Optional<StakeSettings> findStakeSettingsByStakeType(String stakeType);
}
