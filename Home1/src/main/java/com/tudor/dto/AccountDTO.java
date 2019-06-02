package com.tudor.dto;

import com.tudor.staticVariables.AccountCurrency;

import java.math.BigDecimal;

public class AccountDTO {
    private String accountNumber;
    private BigDecimal amount;
    private AccountCurrency currency;

    public AccountDTO() {
    }

    public AccountDTO(String accountNumber, BigDecimal amount, AccountCurrency currency) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.currency = currency;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public AccountCurrency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "accountNumber='" + accountNumber + '\'' +
                ", amount=" + amount +
                ", currency=" + currency +
                '}';
    }
}
