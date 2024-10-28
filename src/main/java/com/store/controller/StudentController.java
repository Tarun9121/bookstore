package com.store.controller;
import com.store.dto.BaseResponse;
import com.store.entity.Student;
import com.store.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/students")
public class StudentController extends UserController<Student> {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        super(studentService);
        this.studentService = studentService;
    }

    @PostMapping("/{studentId}/enroll/{courseId}")
    public ResponseEntity<BaseResponse<String>> enrollInCourse(@PathVariable UUID studentId, @PathVariable UUID courseId) {
        BaseResponse<String> response = studentService.enrollInCourse(studentId, courseId);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
