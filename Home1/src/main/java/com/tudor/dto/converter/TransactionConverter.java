package com.tudor.dto.converter;

import com.tudor.dto.TransactionDTO;
import com.tudor.exception.TransactionException;
import com.tudor.model.Transaction;
import com.tudor.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverter {
    @Autowired
    private AccountService accountService;

    public Transaction convertFromTransactionDTO(TransactionDTO transactionDTO) throws TransactionException {
        if(accountService.findByAccountNumber(transactionDTO.getFromAccount()).isPresent()) {
            return new Transaction(transactionDTO.getToAccount(), transactionDTO.getAmount(), transactionDTO.getDetails(), accountService.findByAccountNumber(transactionDTO.getFromAccount()).get(), transactionDTO.getType());
        }
        throw new TransactionException("invalid user account");
    }

    public TransactionDTO convertToTransactionDTO(Transaction transaction){
        return new TransactionDTO(transaction.getFromAccount().getAccountNumber(), transaction.getAccount(), transaction.getAmount(), transaction.getDetails(), transaction.getType());
    }
}
