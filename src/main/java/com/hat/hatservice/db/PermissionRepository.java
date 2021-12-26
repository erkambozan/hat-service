package com.hat.hatservice.db;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository extends CrudRepository<Permission, UUID> {
	Optional<Permission> findByName(String name);
}

