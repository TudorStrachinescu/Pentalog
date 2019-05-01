package com.tudor.model;

import com.tudor.staticVariables.AccountCurrency;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "account")

public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_account_fk"))
    private User accountUser;

    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "account_type", nullable = false)
    private AccountCurrency accountType;

    @Column(name = "created_time")
    private LocalDateTime created;

    @Column(name = "updated_time")
    private LocalDateTime lastUpdated;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new LinkedList<>();

    public Account() {
    }

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
