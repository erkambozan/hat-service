package com.hat.hatservice.db;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserPermissionRepository extends CrudRepository<UserPermission, UUID> {
	List<UserPermission> findAllByUserId(UUID userId);
	Optional<UserPermission> findByUserIdAndPermissionId(UUID userId, UUID permissionId);
	Optional<UserPermission> findByPermissionId(UUID permissionId);
}

