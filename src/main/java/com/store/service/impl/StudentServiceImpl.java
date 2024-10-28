package com.store.service.impl;
import com.store.dto.BaseResponse;
import com.store.entity.Course;
import com.store.entity.Student;
import com.store.repository.CourseRepository;
import com.store.repository.StudentRepository;
import com.store.service.StudentService;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;
import org.springframework.util.ObjectUtils;

import org.springframework.http.HttpStatus;


@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public BaseResponse<Student> createStudent(Student student) {
        try {
            if (ObjectUtils.isEmpty(student) || ObjectUtils.isEmpty(student.getEmail())) {
                return BaseResponse.error(HttpStatus.BAD_REQUEST, "Student details are required.");
            }

            studentRepository.save(student);
            return BaseResponse.success(HttpStatus.CREATED, "Student created successfully", student);
        } catch (Exception e) {
            return BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create student: " + e.getMessage());
        }
    }

    public BaseResponse<String> softDeleteStudent(UUID studentId) {
        try {
            Optional<Student> studentOpt = studentRepository.findById(studentId);
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                student.setDeleted(true);
                studentRepository.save(student);
                return BaseResponse.success(HttpStatus.OK, "Student deleted successfully");
            } else {
                return BaseResponse.error(HttpStatus.NOT_FOUND, "Student not found.");
            }
        } catch (Exception e) {
            return BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete student: " + e.getMessage());
        }
    }

    public BaseResponse<String> enrollInCourse(UUID studentId, UUID courseId) {
        try {
            if (ObjectUtils.isEmpty(studentId) || ObjectUtils.isEmpty(courseId)) {
                return BaseResponse.error(HttpStatus.BAD_REQUEST, "Student ID and Course ID are required.");
            }

            Optional<Student> studentOpt = studentRepository.findById(studentId);
            Optional<Course> courseOpt = courseRepository.findById(courseId);

            if (studentOpt.isEmpty()) {
                return BaseResponse.error(HttpStatus.NOT_FOUND, "Student not found.");
            }

            if (courseOpt.isEmpty()) {
                return BaseResponse.error(HttpStatus.NOT_FOUND, "Course not found.");
            }

            Student student = studentOpt.get();
            Course course = courseOpt.get();

            course.getStudents().add(student);
            courseRepository.save(course);

            return BaseResponse.success(HttpStatus.OK, "Enrolled successfully in the course.");
        } catch (Exception e) {
            return BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to enroll: " + e.getMessage());
        }
    }
}
