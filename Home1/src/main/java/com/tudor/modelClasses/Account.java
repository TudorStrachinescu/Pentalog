package com.tudor.modelClasses;

import com.tudor.staticVariables.AccountCurrency;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Represents a persons bank account.
 */
@Entity
@Table(name = "account", uniqueConstraints = {
        @UniqueConstraint(columnNames = "account_number")
})

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User accountUser;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "account_type")
    private AccountCurrency accountType;

    @Column(name = "created_time")
    private LocalDateTime created;

    @Column(name = "updated_time")
    private LocalDateTime lastUpdated;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;

    public Account(User accountUser, String accountNumber, AccountCurrency accountType, List<Transaction> transactions) {
        this.accountUser = accountUser;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.transactions = transactions;
        created = LocalDateTime.now();
        lastUpdated = LocalDateTime.now();
        balance = BigDecimal.valueOf(0);
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
        return accountUser.getName();
    }


    /**
     * Returns the Account data in a form writable to file so that it may be loaded afterwards.
     *
     * @return  a string containing all Account data ready to be written to file
     */

    public String toFile(){
        return '\n' + accountNumber + '\t' + accountUser.getName() + '\t' + balance + '\t' + accountType;
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
                ", userName='" + accountUser.getName() + '\'' +
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
                accountUser.getName().equals(account.getUserName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountUser.getName())+ Objects.hash(accountNumber);
    }
}
