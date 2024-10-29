package com.store.service;

import com.store.dto.BaseResponse;
import com.store.dto.UserDto;
import com.store.entity.User;
import com.store.enums.Role;
import com.store.exceptions.ResourceNotFoundException;
import com.store.repository.UserRepository;
import com.store.transform.Convert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Convert transform;

    public BaseResponse<UserDto> createUser(UserDto userDto) {
        try {
            User user = transform.convert(userDto);
            User savedUser = userRepository.save(user);
            userDto.setId(savedUser.getId());
            log.info("User created successfully with ID: {}", savedUser.getId());
            return BaseResponse.success(HttpStatus.CREATED, "User created successfully", userDto);
        } catch (Exception ex) {
            log.error("Failed to create user: {}", ex.getMessage());
            return BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create user. Please try again later.");
        }
    }

    public UserDto getStudentById(UUID id) {
        User student = userRepository.findByIdAndRole(id, Role.STUDENT.toString())
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
        return transform.convert(student);
    }

    public UserDto getTeacherById(UUID id) {
        User teacher = userRepository.findByIdAndRole(id, Role.TEACHER.toString())
                .orElseThrow(() -> new RuntimeException("Teacher not found with ID: " + id));
        return transform.convert(teacher);
    }

    public BaseResponse<String> deleteUser(UUID userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
            user.setDeleted(true);
            userRepository.save(user);
            log.info("User deleted successfully with ID: {}", userId);
            return BaseResponse.success(HttpStatus.OK, "User deleted successfully", null);
        } catch (ResourceNotFoundException ex) {
            log.warn("User deletion failed: {}", ex.getMessage());
            return BaseResponse.error(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.error("Error occurred while deleting user: {}", ex.getMessage());
            return BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while deleting user. Please try again later.");
        }
    }
}
