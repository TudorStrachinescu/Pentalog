package com.tudor.service;

import com.tudor.exception.InvalidAuthenticationException;
import com.tudor.model.Authentication;
import com.tudor.model.User;
import com.tudor.repository.AuthenticationRepository;
import com.tudor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class AuthenticationService {
    private static  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Value("${token.expire.interval}")
    private Integer duration;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    public String authenticateUser(String name, String password, Boolean forced) throws InvalidAuthenticationException {
        if(name == null || password == null){
            throw new InvalidAuthenticationException("missing header parameter");
        }
        User user = new User(name, password);
        Optional<User> stored = userRepository.findByName(user.getName());
        if(stored.isPresent() && user.equals(stored.get())){
            if(authenticationRepository.findByUser(stored.get()).isPresent() && forced != null && forced) {
                authenticationRepository.deleteByUser(stored.get().getId());
            }
            if(authenticationRepository.findByUser(stored.get()).isPresent() && (forced == null || !forced)){
                throw new InvalidAuthenticationException("user already logged");
            }

            Authentication token = new Authentication(stored.get(), LocalDateTime.now().format(formatter) + "_" + stored.get().getName());
            authenticationRepository.save(token);
            return token.getToken();

        }

        throw new InvalidAuthenticationException("invalid credentials");
    }

    public Optional<Authentication> getByToken(String token){
        return authenticationRepository.findByToken(token);
    }

    public void logOut(String token){
        authenticationRepository.deleteByToken(token);
    }

    @Scheduled(cron = "0 * * * * ?")
    public void checkTokens(){
        for(Authentication a : authenticationRepository.findAll()){
            LocalDateTime created = LocalDateTime.parse(a.getToken().split("_")[0], formatter);
            Long temp = LocalDateTime.from(created).until(LocalDateTime.now(), ChronoUnit.SECONDS);
            if(temp >= duration){
                authenticationRepository.delete(a);
            }
        }
    }
}
