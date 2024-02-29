package com.itmo.springproject01.repository;

import com.itmo.springproject01.entity.CustomUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomUserRepository extends CrudRepository<CustomUser, Integer> {
    Optional<CustomUser> findByUsername(String username);
    boolean existsByUsername(String username);
}
