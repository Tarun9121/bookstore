package com.store.service.impl;
import com.store.dto.BaseResponse;
import com.store.entity.Course;
import com.store.repository.CourseRepository;
import com.store.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public BaseResponse<Course> getCourseById(UUID courseId) {
        try {
            if (ObjectUtils.isEmpty(courseId)) {
                return BaseResponse.error(HttpStatus.BAD_REQUEST, "Course ID is required.");
            }

            Optional<Course> courseOpt = courseRepository.findById(courseId);
            return courseOpt.map(course -> BaseResponse.success(HttpStatus.OK, "Course retrieved successfully", course)).orElseGet(() -> BaseResponse.error(HttpStatus.NOT_FOUND, "Course not found."));
        } catch (Exception e) {
            return BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve course: " + e.getMessage());
        }
    }
}
