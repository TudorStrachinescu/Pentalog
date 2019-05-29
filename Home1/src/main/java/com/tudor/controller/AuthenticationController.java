package com.tudor.controller;

import com.tudor.exception.InvalidAuthenticationException;
import com.tudor.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController("/authentication")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestHeader HttpHeaders headers, @PathParam("forced") Boolean forced){
        headers.get("name");
        try{
            String response = authenticationService.authenticateUser(headers.getFirst("name"), headers.getFirst("password"), forced);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidAuthenticationException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void userLogout(@RequestParam(value = "token") String token){
        authenticationService.logOut(token);
    }
}
