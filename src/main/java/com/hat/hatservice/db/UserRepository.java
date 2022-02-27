package com.hat.hatservice.db;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
	Optional<User> findUserByEmail(String email);
	List<User> findByReferenceId(UUID id);
}
