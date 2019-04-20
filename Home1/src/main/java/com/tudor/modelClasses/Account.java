package com.tudor.modelClasses;

import com.tudor.staticVariables.AccountCurrency;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a persons bank account.
 */

public class Account {
    private String accountNumber;
    private String userName;
    private BigDecimal balance;
    private AccountCurrency accountType;

    /**
     * Creates a new Account with the given parameters.
     *
     * @param accountNumber the account number
     * @param userName      the name associated with the account
     * @param balance       the amount of money available in the account
     * @param accountType   the currency used with the account
     */

    public Account(String accountNumber, String userName, BigDecimal balance, AccountCurrency accountType) {
        this.accountNumber = accountNumber;
        this.userName = userName;
        this.balance = balance;
        this.accountType = accountType;
    }


    /**
     * Gets the account number or the Account
     *
     * @return  accountNumber
     */

    private String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Gets the currency of the Account
     *
     * @return  accountType
     * @see AccountCurrency
     */

    public AccountCurrency getAccountType() {
        return accountType;
    }

    /**
     * Gets the Account owner name
     *
     * @return  userName
     */

    public String getUserName() {
        return userName;
    }


    /**
     * Returns the Account data in a form writable to file so that it may be loaded afterwards.
     *
     * @return  a string containing all Account data ready to be written to file
     */

    public String toFile(){
        return '\n' + accountNumber + '\t' + userName + '\t' + balance + '\t' + accountType;
    }

    /**
     * Subtracts a given amount from the amount available in the Account.
     * <p>
     * If the amount given is larger than the available balance the operation fails.
     *
     * @param amount    the amount to be subtracted from the current Account balance
     * @return          <code>true</code> if amount has been subtracted and <code>false</code> otherwise
     */

    public boolean withdrawal(BigDecimal amount){
        if(this.balance.compareTo(amount) >= 0){
            this.balance = this.balance.subtract(amount);
            return true;
        }

        return false;
    }

    /**
     * Adds a given amount tot the amount available in the Account.
     *
     * @param amount    the amount to be added to the current Account balance
     */

    public void deposit(BigDecimal amount){
        balance = balance.add(amount);
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
}
