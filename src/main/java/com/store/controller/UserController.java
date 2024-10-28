package com.store.controller;


import com.store.dto.BaseResponse;
import com.store.dto.UserDto;
import com.store.entity.User;
import com.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

public abstract class UserController<T extends User> {

    private final UserService<T> userService;

    public UserController(UserService<T> userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public T createUser(@RequestBody T user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

    @GetMapping("/email/{email}")
    public T getUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }
}
