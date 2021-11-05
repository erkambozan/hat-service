package com.hat.hatservice.db;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserTotalBalanceRepository extends CrudRepository<UserTotalBalance, UUID> {

}
