package com.store.dto;

import com.store.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data @AllArgsConstructor @NoArgsConstructor
public class CourseDto {
    private UUID id;
    private String name;
    private String description;
    private UserDto teacher;
    private String teacherEmail;  // Optional: If you want teacher details.
}
