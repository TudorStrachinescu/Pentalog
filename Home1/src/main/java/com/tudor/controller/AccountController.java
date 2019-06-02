package com.tudor.controller;

import com.tudor.dto.AccountDTO;
import com.tudor.dto.converter.AccountConverter;
import com.tudor.model.Account;
import com.tudor.model.Authentication;
import com.tudor.service.AccountService;
import com.tudor.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController("/account")
public class AccountController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountConverter accountConverter;

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDTO>> getAccounts(@RequestHeader HttpHeaders headers){
        Optional<Authentication> token = authenticationService.getByToken(headers.getFirst("token"));

        if(token.isPresent() && accountService.hasAccountsForUser(token.get().getUser())){
            List<AccountDTO> accounts = new LinkedList<>();
            for(Account account : accountService.getAccountsForUser(token.get().getUser())){
                accounts.add(accountConverter.convertToAccountDTO(account));
            }
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/accounts")
    public ResponseEntity<AccountDTO> createAccount(@RequestHeader HttpHeaders headers, @RequestBody AccountDTO account){
        Optional<Authentication> token = authenticationService.getByToken(headers.getFirst("token"));

        if(token.isPresent() && accountService.saveAccount(account, token.get().getUser())){
            return new ResponseEntity<>(account, HttpStatus.OK);
        }

        return ResponseEntity.badRequest().build();
    }
}
