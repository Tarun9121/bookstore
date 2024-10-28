package com.store.repository;

import com.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository<T extends User> extends JpaRepository<T, UUID> {
    Optional<T> findByEmail(String email);
}
