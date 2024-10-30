package com.store.service;

import com.store.dto.BaseResponse;
import com.store.dto.UserDto;
import com.store.entity.Course;
import com.store.entity.Enrollment;
import com.store.entity.User;
import com.store.exceptions.ResourceNotFoundException;
import com.store.repository.EnrollmentRepository;
import com.store.repository.UserRepository;
import com.store.transform.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private UserRepository studentRepository;
    @Autowired
    private CourseService courseService;
    @Autowired
    private Convert transform;

    public BaseResponse<Enrollment> enrollCourse(UUID studentId, UUID courseId) {
        try {
            if (enrollmentRepository.findByStudentIdAndCourseIdAndIsDeletedFalse(studentId, courseId).isPresent()) {
                log.warn("Duplicate enrollment attempt: Student {} already enrolled in course {}", studentId, courseId);
                return BaseResponse.error(HttpStatus.CONFLICT, "Student is already enrolled in this course.");
            }

            Enrollment enrollment = new Enrollment();
            User student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Student not found with this id: " + studentId));
            Course course = courseService.getCourseById(courseId);
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
            log.info("Student {} enrolled in course {} successfully.", studentId, courseId);
            return BaseResponse.success(HttpStatus.CREATED, "Enrolled successfully", savedEnrollment);
        } catch (Exception ex) {
            log.error("Failed to enroll in course: {}", ex.getMessage());
            return BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while enrolling in course. Please try again later.");
        }
    }

    public BaseResponse<List<Enrollment>> getEnrollmentsByStudentId(UUID studentId) {
        try {
            List<Enrollment> enrollments = enrollmentRepository.findAllByStudentIdAndIsDeletedFalse(studentId);
            log.info("Fetched enrollments for student ID: {}", studentId);
            return BaseResponse.success(HttpStatus.OK, "Enrollments fetched successfully", enrollments);
        } catch (Exception ex) {
            log.error("Failed to fetch enrollments: {}", ex.getMessage());
            return BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while fetching enrollments. Please try again later.");
        }
    }

    public BaseResponse<Enrollment> updateEnrollmentStatus(UUID enrollmentId, Enrollment.Status status) {
        try {
            Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id: " + enrollmentId));
            enrollment.setEnrollmentStatus(status);
            Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);
            log.info("Updated enrollment status for ID: {} to {}", enrollmentId, status);
            return BaseResponse.success(HttpStatus.OK, "Enrollment status updated successfully", updatedEnrollment);
        } catch (ResourceNotFoundException ex) {
            log.warn("Failed to update enrollment status: {}", ex.getMessage());
            return BaseResponse.error(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.error("Error occurred while updating enrollment status: {}", ex.getMessage());
            return BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while updating enrollment status. Please try again later.");
        }
    }
}
