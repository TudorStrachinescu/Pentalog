package com.tudor.dto.converter;

import com.tudor.dto.AccountDTO;
import com.tudor.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter {
    public AccountConverter() {
    }

    public Account convertFromAccountDTO(AccountDTO accountDTO){
        return new Account(null, accountDTO.getAccountNumber(), accountDTO.getAmount(), accountDTO.getCurrency());
    }

    public AccountDTO convertToAccountDTO(Account account){
        return new AccountDTO(account.getAccountNumber(), account.getBalance(), account.getAccountType());
    }
}
