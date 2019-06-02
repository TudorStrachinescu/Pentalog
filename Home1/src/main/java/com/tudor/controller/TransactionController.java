package com.tudor.controller;

import com.tudor.dto.TransactionDTO;
import com.tudor.dto.converter.TransactionConverter;
import com.tudor.exception.TransactionException;
import com.tudor.model.Authentication;
import com.tudor.model.Transaction;
import com.tudor.service.AuthenticationService;
import com.tudor.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController("/transaction")
public class TransactionController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionConverter transactionConverter;

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDTO>> getAccounts(@RequestHeader HttpHeaders headers){
        Optional<Authentication> token = authenticationService.getByToken(headers.getFirst("token"));

        if(token.isPresent()){
            List<TransactionDTO> transactions = new LinkedList<>();
            for(Transaction transaction : transactionService.getTransactionsForUser(token.get().getUser())){
                transactions.add(transactionConverter.convertToTransactionDTO(transaction));
            }
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        }

        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/transactions")
    public ResponseEntity<String> transferCredit(@RequestHeader HttpHeaders headers, @RequestBody TransactionDTO transactionDTO){
        Optional<Authentication> token = authenticationService.getByToken(headers.getFirst("token"));

        if(token.isPresent()){
            try{
                Transaction t = transactionConverter.convertFromTransactionDTO(transactionDTO);
                transactionService.executeTransaction(t, token.get().getUser());
                return new ResponseEntity<>("transaction successful", HttpStatus.OK);
            } catch (TransactionException e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }

        return ResponseEntity.badRequest().build();
    }
}
