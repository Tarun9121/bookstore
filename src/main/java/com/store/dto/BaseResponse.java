package com.store.dto;

import com.store.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseResponse<T> {
    private HttpStatus status;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    // Private constructor to enforce usage of factory methods
    private BaseResponse(HttpStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    // Factory method for success responses
    public static <T> BaseResponse<T> success(HttpStatus status, String message, T data) {
        return new BaseResponse<>(status, message, data);
    }

    public static <T> BaseResponse<T> success(HttpStatus status, String message) {
        return new BaseResponse<>(status, message, null);
    }

    // Factory method for error responses
    public static <T> BaseResponse<T> error(HttpStatus status, String message) {
        return new BaseResponse<>(status, message, null);
    }
}

