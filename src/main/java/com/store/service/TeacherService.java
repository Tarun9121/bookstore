package com.store.service;
import com.store.dto.BaseResponse;
import com.store.entity.Course;
import com.store.entity.Teacher;

import java.util.UUID;
import java.util.UUID;

public interface TeacherService {
    BaseResponse<Teacher> createTeacher(Teacher teacher);

    BaseResponse<String> softDeleteTeacher(UUID teacherId);

    BaseResponse<Course> createCourse(UUID teacherId, Course course);
}
