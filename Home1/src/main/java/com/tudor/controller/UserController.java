package com.tudor.controller;

import com.tudor.dto.UserDTO;
import com.tudor.dto.converter.UserConverter;
import com.tudor.model.Authentication;
import com.tudor.model.User;
import com.tudor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserConverter userConverter;

    public UserController() {
    }

    @GetMapping
    public String getUser(@RequestBody User user) {
        Optional<Authentication> auth = userService.checkUser(user);
        if(auth.isPresent()){
            return auth.get().getToken();
        }

        return "invalid credentials";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody User user) {
        return this.userConverter.convertToUserDTO(this.userService.createUser(user));
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody User user) {
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestParam String username) {
        this.userService.deleteUserByUsername(username);
    }

    @GetMapping({"/error"})
    public UserDTO getError() throws Exception {
        throw new Exception();
    }
}
