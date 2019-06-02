package com.tudor.service;

import com.tudor.exception.TransactionException;
import com.tudor.model.Account;
import com.tudor.model.Transaction;
import com.tudor.model.User;
import com.tudor.repository.TransactionRepository;
import com.tudor.staticVariables.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private NotificationService notificationService;

    public List<Transaction> getTransactionsForUser(User user){
        return transactionRepository.getTransactionsForUser(user);
    }

    public void executeTransaction(Transaction transaction, User user) throws TransactionException{
        Optional<Account> account = accountService.findByAccountNumber(transaction.getAccount());

        if(!account.isPresent()){
            throw new TransactionException("invalid to account");
        }

        if(transaction.getFromAccount().getBalance().compareTo(transaction.getAmount()) < 0){
            throw new TransactionException("insufficient funds");
        }

        Account accountFrom = transaction.getFromAccount();
        accountFrom.withdrawal(transaction.getAmount());

        Account accountTo = account.get();
        accountTo.deposit(transaction.getAmount());

        if(accountService.updateAccount(accountFrom) && accountService.updateAccount(accountTo)){
            transactionRepository.save(new Transaction(transaction.getAccount(), transaction.getAmount(), transaction.getDetails(), transaction.getFromAccount(), Type.OUTGOING));
            transactionRepository.save(new Transaction(transaction.getFromAccount().getAccountNumber(), transaction.getAmount(), transaction.getDetails(), account.get(), Type.INCOMING));
            notificationService.addNotification(user, transaction.getDetails());
        }else {
            throw new TransactionException("error updating accounts");
        }
    }
}
