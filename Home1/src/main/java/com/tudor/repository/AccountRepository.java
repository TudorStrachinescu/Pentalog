package com.tudor.repository;

import com.tudor.model.Account;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
    @Transactional
    Optional<Account> findByaccountNumber(String accountNumber);
}
