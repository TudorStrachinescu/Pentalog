package com.tudor.service;

import com.tudor.dto.AccountDTO;
import com.tudor.model.Account;
import com.tudor.model.User;
import com.tudor.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * This class contains the methods for operations that can be made with an Account.
 */

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAccountsForUser(User user){
        return accountRepository.findAllByAccountUser(user);
    }

    private boolean isValidAccountFormat(String accountNumber) {
        return accountNumber.toUpperCase().matches("^RO\\d{2}\\p{Upper}{4}\\d{16}$");
    }

    public boolean hasAccountsForUser(User user){
        return accountRepository.existsByAccountUser(user);
    }

    public boolean saveAccount(AccountDTO account, User user){
        if(!accountRepository.findByAccountNumber(account.getAccountNumber()).isPresent() && isValidAccountFormat(account.getAccountNumber())){
            accountRepository.save(new Account(user, account.getAccountNumber(), account.getAmount(), account.getCurrency()));
            return true;
        }

        return false;
    }

    public Optional<Account> findByAccountNumber(String accountNumber){
        return accountRepository.findByAccountNumber(accountNumber);
    }

    public boolean updateAccount(Account account){
        if(accountRepository.existsByAccountNumber(account.getAccountNumber())){
           accountRepository.save(account);
           return true;
        }

        return false;
    }
}
