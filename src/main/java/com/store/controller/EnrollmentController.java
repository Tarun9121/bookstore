package com.store.controller;

import com.store.dto.BaseResponse;
import com.store.entity.Enrollment;
import com.store.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<BaseResponse<Enrollment>> enrollCourse(@PathVariable UUID studentId, @PathVariable UUID courseId) {
        return ResponseEntity.ok(enrollmentService.enrollCourse(studentId, courseId));
    }

    @GetMapping("/student/{studentId}")
    public
    ResponseEntity<BaseResponse<List<Enrollment>>> getEnrollments(@PathVariable UUID studentId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByStudentId(studentId));
    }

    @PatchMapping("/{enrollmentId}/status")
    public ResponseEntity<Void> updateEnrollmentStatus(@PathVariable UUID enrollmentId, @RequestParam Enrollment.Status status) {
        enrollmentService.updateEnrollmentStatus(enrollmentId, status);
        return ResponseEntity.noContent().build();
    }
}
