package com.tudor.repository;

import com.tudor.model.Account;

import com.tudor.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
    @Transactional
    Optional<Account> findByAccountNumber(String accountNumber);

    @Transactional
    List<Account> findAllByAccountUser(User user);

    @Transactional
    boolean existsByAccountUser(User user);

    @Transactional
    boolean existsByAccountNumber(String accountNumber);
}
