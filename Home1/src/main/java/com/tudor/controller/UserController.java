package com.tudor.controller;

import com.tudor.dto.UserDTO;
import com.tudor.dto.converter.UserConverter;
import com.tudor.model.Authentication;
import com.tudor.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController("/user")
public class UserController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserConverter userConverter;

    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUser(@RequestHeader HttpHeaders headers){
        Optional<Authentication> token = authenticationService.getByToken(headers.getFirst("token"));

        if(token.isPresent()){
            return new ResponseEntity<>(userConverter.convertToUserDTO(token.get().getUser()), HttpStatus.OK);
        }

        return ResponseEntity.badRequest().build();
    }
}
