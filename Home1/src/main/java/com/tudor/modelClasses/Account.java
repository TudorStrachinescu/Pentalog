package com.tudor.modelClasses;

import com.tudor.staticVariables.AccountCurrency;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


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
    private List<Transaction> transactions = new LinkedList<>();

    public Account(User accountUser, String accountNumber, BigDecimal balance, AccountCurrency accountType) {
        this.accountUser = accountUser;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;
        created = LocalDateTime.now();
        lastUpdated = LocalDateTime.now();
    }

    private String getAccountNumber() {
        return accountNumber;
    }

    public AccountCurrency getAccountType() {
        return accountType;
    }

    public String getUserName() {
        return accountUser.getName();
    }

    public String toFile(){
        return '\n' + accountNumber + '\t' + accountUser.getName() + '\t' + balance + '\t' + accountType;
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
