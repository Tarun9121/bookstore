package com.store.service;
import com.store.dto.BaseResponse;
import com.store.entity.Course;

import java.util.UUID;

public interface CourseService {
    BaseResponse<Course> getCourseById(UUID courseId);
}
