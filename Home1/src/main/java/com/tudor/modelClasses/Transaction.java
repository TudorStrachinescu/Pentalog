package com.tudor.modelClasses;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "to_account")
    private String toAccount;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "details")
    private String details;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "account_id")
    private Account account;

    public Transaction(String toAccount, BigDecimal balance, String details, Account account) {
        this.toAccount = toAccount;
        this.balance = balance;
        this.details = details;
        this.account = account;
        createdTime = LocalDateTime.now();
    }
}
