package com.store.service.impl;
import com.store.dto.BaseResponse;
import com.store.entity.Course;
import com.store.entity.Teacher;
import com.store.repository.CourseRepository;
import com.store.repository.TeacherRepository;
import com.store.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;
import java.util.UUID;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository, CourseRepository courseRepository) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public BaseResponse<Teacher> createTeacher(Teacher teacher) {
        try {
            if (ObjectUtils.isEmpty(teacher) || ObjectUtils.isEmpty(teacher.getEmail())) {
                return BaseResponse.error(HttpStatus.BAD_REQUEST, "Teacher details are required.");
            }

            teacher.setId(UUID.randomUUID());
            teacher.setDeleted(false);
            teacherRepository.save(teacher);
            return BaseResponse.success(HttpStatus.CREATED, "Teacher created successfully", teacher);
        } catch (Exception e) {
            return BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create teacher: " + e.getMessage());
        }
    }

    @Override
    public BaseResponse<String> softDeleteTeacher(UUID teacherId) {
        try {
            Optional<Teacher> teacherOpt = teacherRepository.findById(teacherId);
            if (teacherOpt.isPresent()) {
                Teacher teacher = teacherOpt.get();
                teacher.setDeleted(true);
                teacherRepository.save(teacher);
                return BaseResponse.success(HttpStatus.OK, "Teacher deleted successfully");
            } else {
                return BaseResponse.error(HttpStatus.NOT_FOUND, "Teacher not found.");
            }
        } catch (Exception e) {
            return BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete teacher: " + e.getMessage());
        }
    }

    @Override
    public BaseResponse<Course> createCourse(UUID teacherId, Course course) {
        try {
            if (ObjectUtils.isEmpty(teacherId) || ObjectUtils.isEmpty(course)) {
                return BaseResponse.error(HttpStatus.BAD_REQUEST, "Teacher ID and Course details are required.");
            }

            Optional<Teacher> teacherOpt = teacherRepository.findById(teacherId);
            if (!teacherOpt.isPresent()) {
                return BaseResponse.error(HttpStatus.NOT_FOUND, "Teacher not found.");
            }

            Teacher teacher = teacherOpt.get();
            course.setId(UUID.randomUUID());
            course.setTeacher(teacher);
            courseRepository.save(course);

            return BaseResponse.success(HttpStatus.CREATED, "Course created successfully", course);
        } catch (Exception e) {
            return BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create course: " + e.getMessage());
        }
    }
}
