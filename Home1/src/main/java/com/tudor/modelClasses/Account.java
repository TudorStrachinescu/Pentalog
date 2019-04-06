package com.tudor.modelClasses;

import com.tudor.staticVariables.AccountCurrency;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {
    private String accountNumber;
    private String userName;
    private BigDecimal balance;
    private AccountCurrency accountType;

    public Account(){}

    public Account(String accountNumber, String userName, BigDecimal balance, AccountCurrency accountType) {
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
        return Objects.hash(userName)+ Objects.hash(accountNumber);
    }

    private String getAccountNumber() {
        return accountNumber;
    }

    public AccountCurrency getAccountType() {
        return accountType;
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

    public String toFile(){
        return '\n' + accountNumber + '\t' + userName + '\t' + balance + '\t' + accountType;
    }

    public boolean withdrawal(BigDecimal amount){
        if(this.balance.compareTo(amount) >= 0){
            this.balance = this.balance.subtract(amount);
            return true;
        }

        return false;
    }

    public void deposit(BigDecimal amount){
        balance = balance.add(amount);
    }
}
