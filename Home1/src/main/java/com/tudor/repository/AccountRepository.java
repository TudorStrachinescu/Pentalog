package com.tudor.repository;

import com.tudor.model.Account;
import com.tudor.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

    @Query("select a from Account a where a.accountNumber = ?1")
    Optional<Account> checkAccount(String accountNumber);

//    List<Account> getUserAccounts();

   // void updateAccount(Account account, Transaction transaction);
}
