package com.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data @AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private String role;
}
