package com.tudor.dto;

import com.tudor.staticVariables.Type;

import java.math.BigDecimal;

public class TransactionDTO {
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private String details;
    private Type type;

    public TransactionDTO() {
    }

    public TransactionDTO(String fromAccount, String toAccount, BigDecimal amount, String details, Type type) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.details = details;
        this.type = type;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public String getToAccount() {
        return toAccount;
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
}
