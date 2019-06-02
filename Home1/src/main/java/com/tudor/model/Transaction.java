package com.tudor.model;

import com.tudor.staticVariables.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")

public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "to_account")
    private String account;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "details")
    private String details;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "type")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "from_account", foreignKey = @ForeignKey(name = "account_transaction_fk"))
    private Account fromAccount;

    public Transaction() {
    }

    public String getAccount() {
        return account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDetails() {
        return details;
    }

    public Type getType() {
        return type;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public Transaction(String account, BigDecimal amount, String details, Account fromAccount, Type type) {
        this.account = account;
        this.amount = amount;
        this.details = details;
        this.fromAccount = fromAccount;
        this.type = type;
        createdTime = LocalDateTime.now();
    }
}
