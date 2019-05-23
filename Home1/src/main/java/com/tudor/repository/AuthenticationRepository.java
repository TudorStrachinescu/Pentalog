package com.tudor.repository;

import com.tudor.model.Authentication;
import org.springframework.data.repository.CrudRepository;

public interface AuthenticationRepository extends CrudRepository<Authentication, Integer> {
}
