package com.tudor.service;

import com.tudor.dto.converter.UserConverter;
import com.tudor.model.Authentication;
import com.tudor.model.User;
import com.tudor.repository.AccountRepository;
import com.tudor.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Class used for user authentication operations.
 */

@Service
public class UserService {

    @Autowired
    private AccountRepository accountData;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserConverter converter;

    public Optional<Authentication> checkUser(User user){
        Optional<User> stored = userRepository.findByName(user.getName());
        if(stored.isPresent() && user.equals(stored.get())){
            Authentication token = new Authentication(stored.get(), LocalDate.now() + stored.get().getName());
            return Optional.of(token);
        }

        return Optional.empty();
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public void deleteUserByUsername(String username){
        userRepository.deleteByName(username);
    }
}