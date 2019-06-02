package com.tudor.repository;

import com.tudor.model.Transaction;
import com.tudor.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    @Transactional
    @Query(value = "select a.transactions from Account a where a.accountUser = :userid")
    List<Transaction> getTransactionsForUser(@Param("userid") User user);
}
