package com.tudor.repository;

import com.tudor.model.Authentication;
import com.tudor.model.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface AuthenticationRepository extends CrudRepository<Authentication, Integer> {
    @Transactional
    Optional<Authentication> findByToken(String token);

    @Transactional
    Optional<Authentication> findByUser(User user);

    @Transactional
    void deleteByToken(String Token);

    @Transactional
    void deleteByUser(User user);
}
