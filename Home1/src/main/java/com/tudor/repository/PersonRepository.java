package com.tudor.repository;

import com.tudor.model.Person;
import com.tudor.model.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    @Transactional
    Optional<Person> findByUser(User user);
}
