package com.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data @AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDto {
    private UUID id;
    private UUID studentId;
    private UUID courseId;
    private EnrollmentStatus status;

    public enum EnrollmentStatus {
        ENROLLED, COMPLETED, DROPPED
    }
}
