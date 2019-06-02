package com.tudor.service;

import com.tudor.model.Person;
import com.tudor.model.User;
import com.tudor.repository.PersonRepository;
import com.tudor.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Class used for user authentication operations.
 */

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonRepository personRepository;

    public Optional<Person> getUserDetails(User user){
        return personRepository.findByUser(user);
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public void deleteUserByUsername(String username){
        userRepository.deleteByName(username);
    }
}