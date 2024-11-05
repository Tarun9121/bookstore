package com.store.service;

import com.store.dto.BaseResponse;
import com.store.dto.CourseDto;
import com.store.dto.UserDto;
import com.store.entity.Course;
import com.store.exceptions.ResourceNotFoundException;
import com.store.repository.CourseRepository;
import com.store.transform.Convert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserService teacherService;
    @Autowired
    private Convert transform;

    public BaseResponse<Course> createCourse(CourseDto courseDto, UUID teacherId) {
        try {
            UserDto teacher = teacherService.getTeacherById(teacherId);
            courseDto.setTeacher(teacher);
            Course course = transform.convert(courseDto);
            Course savedCourse = courseRepository.save(course);
            log.info("Course created successfully with ID: {}", savedCourse.getId());
            return BaseResponse.success(HttpStatus.CREATED, "Course created successfully", null);
        } catch (Exception ex) {
            log.error("Failed to create course: {}", ex.getMessage());
            return BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create course. Please try again later.");
        }
    }

    public Course getCourseById(UUID courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException(  "Course not found") );
        return course;
    }

    public BaseResponse<String> deleteCourse(UUID courseId) {
        try {
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));
            course.setDeleted(true);
            courseRepository.save(course);
            log.info("Course deleted successfully with ID: {}", courseId);
            return BaseResponse.success(HttpStatus.OK, "Course deleted successfully", null);
        } catch (ResourceNotFoundException ex) {
            log.warn("Course deletion failed: {}", ex.getMessage());
            return BaseResponse.error(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.error("Error occurred while deleting course: {}", ex.getMessage());
            return BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while deleting course. Please try again later.");
        }
    }
}
