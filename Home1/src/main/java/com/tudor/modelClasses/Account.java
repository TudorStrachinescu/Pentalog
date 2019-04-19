package com.tudor.modelClasses;

import com.tudor.staticVariables.AccountCurrency;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 */

public class Account {
    private String accountNumber;
    private String userName;
    private BigDecimal balance;
    private AccountCurrency accountType;

    /**
     *
     * @param accountNumber
     * @param userName
     * @param balance
     * @param accountType
     */

    public Account(String accountNumber, String userName, BigDecimal balance, AccountCurrency accountType) {
        this.accountNumber = accountNumber;
        this.userName = userName;
        this.balance = balance;
        this.accountType = accountType;
    }

    /**
     *
     * @param o
     * @return
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountNumber.equals(account.getAccountNumber()) &&
                userName.equals(account.getUserName());
    }

    /**
     *
     * @return
     */

    @Override
    public int hashCode() {
        return Objects.hash(userName)+ Objects.hash(accountNumber);
    }

    /**
     *
     * @return
     */

    private String getAccountNumber() {
        return accountNumber;
    }

    /**
     *
     * @return
     */

    public AccountCurrency getAccountType() {
        return accountType;
    }

    /**
     *
     * @return
     */

    public String getUserName() {
        return userName;
    }

    /**
     *
     * @return
     */

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", userName='" + userName + '\'' +
                ", balance=" + balance +
                ", accountType=" + accountType +
                '}';
    }

    /**
     *
     * @return
     */

    public String toFile(){
        return '\n' + accountNumber + '\t' + userName + '\t' + balance + '\t' + accountType;
    }

    /**
     *
     * @param amount
     * @return
     */

    public boolean withdrawal(BigDecimal amount){
        if(this.balance.compareTo(amount) >= 0){
            this.balance = this.balance.subtract(amount);
            return true;
        }

        return false;
    }

    /**
     *
     * @param amount
     */

    public void deposit(BigDecimal amount){
        balance = balance.add(amount);
    }
}
