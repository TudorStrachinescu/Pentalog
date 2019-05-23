package com.tudor.controller;

import com.tudor.dto.UserDTO;
import com.tudor.dto.converter.UserConverter;
import com.tudor.model.User;
import com.tudor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserConverter userConverter;

    public UserController() {
    }

    @GetMapping
    public UserDTO getUser(@PathParam("name") String name, @PathParam("password") String password) {
        return this.userConverter.convertToUserDTO(this.userService.getUserByNameAndPassword(name, password).get());
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
