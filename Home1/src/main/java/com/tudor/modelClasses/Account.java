package com.tudor.modelClasses;

import com.tudor.staticVariables.AccountCurrency;

import java.util.Objects;

public class Account {
    private String accountNumber;
    private String userName;
    private double balance;
    private AccountCurrency accountType;

    public Account(String accountNumber, String userName, double balance, AccountCurrency accountType) {
        this.accountNumber = accountNumber;
        this.userName = userName;
        this.balance = balance;
        this.accountType = accountType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountNumber.equals(account.getAccountNumber()) &&
                userName.equals(account.getUserName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }

    private String getAccountNumber() {
        return accountNumber;
    }

    public String getUserName() {
        return userName;
    }
    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", userName='" + userName + '\'' +
                ", balance=" + balance +
                ", accountType=" + accountType +
                '}';
    }
}
