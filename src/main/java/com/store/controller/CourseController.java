package com.store.controller;

import com.store.dto.BaseResponse;
import com.store.dto.CourseDto;
import com.store.entity.Course;
import com.store.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/teacher/{teacherId}")
    public ResponseEntity<BaseResponse<Course>> createCourse(@RequestBody CourseDto courseDto, @PathVariable UUID teacherId) {
        return ResponseEntity.ok(courseService.createCourse(courseDto, teacherId));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable UUID courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }
}