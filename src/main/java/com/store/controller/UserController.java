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

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/public/register")
    public ResponseEntity<BaseResponse<UserDto>> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @GetMapping("/student/{userId}")
    public ResponseEntity<BaseResponse<UserDto>> getStudentById(@PathVariable("userId") UUID userId) {
        return userService.getStudentById(userId);
    }

    @GetMapping("/teacher/{userId}")
    public BaseResponse<UserDto> getTeacherById(@PathVariable("userId") UUID userId) {
        return BaseResponse.success(HttpStatus.OK, "successfully fetched the user data", userService.getTeacherById(userId));
    }

    @GetMapping("/student")
    public String testStudent() {
        return "student details";
    }

    @GetMapping("/test/authorites")
    public String testAuthorites() {
        return "working!!!!!";
    }

    @GetMapping("/teacher")
    public String testTeacher() {
        return "teacher details";
    }

    @DeleteMapping("/{userId}")
    public BaseResponse<String> deleteUser(@PathVariable UUID userId) {
        return userService.deleteUser(userId);
    }
}
