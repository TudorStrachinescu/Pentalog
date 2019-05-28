package com.tudor.service;

import com.tudor.model.User;
import com.tudor.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Class used for user authentication operations.
 */

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user){
        return userRepository.save(user);
    }

    public void deleteUserByUsername(String username){
        userRepository.deleteByName(username);
    }
}