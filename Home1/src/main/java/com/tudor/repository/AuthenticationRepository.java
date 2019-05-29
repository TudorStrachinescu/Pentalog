package com.tudor.repository;

import com.tudor.model.Authentication;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface AuthenticationRepository extends CrudRepository<Authentication, Integer> {
    @Transactional
    Optional<Authentication> findByUser(Integer user);

    @Transactional
    void deleteByToken(String Token);

    @Transactional
    void deleteByUser(Integer user);
}
