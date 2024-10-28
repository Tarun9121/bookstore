package com.store.controller;

import com.store.dto.BaseResponse;
import com.store.entity.Course;
import com.store.entity.Teacher;
import com.store.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/teachers")
public class TeacherController extends UserController<Teacher> {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
//        super(teacherService);
        this.teacherService = teacherService;
    }

    @PostMapping("/{teacherId}/create-course")
    public ResponseEntity<BaseResponse<Course>> createCourse(@PathVariable UUID teacherId, @RequestBody Course course) {
        try {
            // Validate important fields
            if (ObjectUtils.isEmpty(teacherId) || ObjectUtils.isEmpty(course)) {
                return ResponseEntity.badRequest().body(BaseResponse.error(HttpStatus.BAD_REQUEST, "Teacher ID and Course details are required."));
            }

            Course createdCourse = teacherService.createCourse(teacherId, course).getData();
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(BaseResponse.success(HttpStatus.CREATED, "Course created successfully", createdCourse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create course: " + e.getMessage()));
        }
    }
}
