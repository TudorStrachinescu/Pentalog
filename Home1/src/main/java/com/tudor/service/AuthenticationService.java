package com.tudor.service;

import com.tudor.model.Authentication;
import com.tudor.model.User;
import com.tudor.repository.AuthenticationRepository;
import com.tudor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationRepository authenticationRepository;

    public String authenticateUser(User user){
        Optional<User> stored = userRepository.findByName(user.getName());
        if(stored.isPresent() && user.equals(stored.get())){
            if(authenticationRepository.findByUser(stored.get().getId()).isPresent()){
                return "user already logged";
            }

            Authentication token = new Authentication(stored.get().getId(), LocalDateTime.now() + stored.get().getName());
            authenticationRepository.save(token);
            return token.getToken();

        }

        return "invalid credentials";
    }

    public void logOut(String token){
        authenticationRepository.deleteByToken(token);
    }
}
