package com.tudor.repository;

import com.tudor.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Class used to load user data from a local resource file.
 */

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Bean
    Optional<User> findByNameAndPassword(String name, String password);
}