package com.tudor.service;

import com.tudor.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * This class contains the methods for operations that can be made with an Account.
 */

@Service
public class AccountService {

    private NotificationService notifications = new NotificationService();
    @Autowired
    private AccountRepository accountCheck;


    private boolean isValidAccountFormat(String accountNumber) {
        return accountNumber.matches("^RO\\d{2}\\p{Upper}{4}\\d{16}$");
    }
}
