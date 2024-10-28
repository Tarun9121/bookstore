package com.store.controller;

import com.store.dto.BaseResponse;
import com.store.entity.Course;
import com.store.repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<Course>>> getAllCourses() {
        try {
            List<Course> courses = courseRepository.findAll();
            return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, "Courses retrieved successfully", courses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve courses: " + e.getMessage()));
        }
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<BaseResponse<Course>> getCourseById(@PathVariable UUID courseId) {
        try {
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new IllegalArgumentException("Course not found"));
            return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, "Course retrieved successfully", course));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve course: " + e.getMessage()));
        }
    }
}
