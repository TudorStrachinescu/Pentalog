package com.tudor.controller;

import com.tudor.model.User;
import com.tudor.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("/authentication")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public String userLogin(@RequestBody User user){
        return authenticationService.authenticateUser(user);
    }

    @DeleteMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void userLogout(@RequestParam(value = "token") String token){
        authenticationService.logOut(token);
    }
}
