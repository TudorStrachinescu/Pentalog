package com.tudor.repository;

import com.tudor.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Class used to load user data from a local resource file.
 */

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Transactional
    Optional<User> findByNameAndPassword(String name, String password);

    @Transactional
    void deleteByName(String name);
}