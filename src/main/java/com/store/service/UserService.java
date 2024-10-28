package com.store.service;

import com.store.entity.User;

import java.util.UUID;

public interface UserService<T extends User> {
    T createUser(T user);
    void deleteUser(UUID id);
    T findUserByEmail(String email);
}
