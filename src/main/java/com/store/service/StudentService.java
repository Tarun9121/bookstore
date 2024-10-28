package com.store.service;
import com.store.dto.BaseResponse;
import com.store.entity.Student;

import java.util.UUID;
import java.util.UUID;

public interface StudentService {
    BaseResponse<Student> createStudent(Student student);

    BaseResponse<String> softDeleteStudent(UUID studentId);

    BaseResponse<String> enrollInCourse(UUID studentId, UUID courseId);
}
