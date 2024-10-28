package com.store.transform;

import com.store.dto.CourseDto;
import com.store.dto.UserDto;
import com.store.entity.Course;
import com.store.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class Convert {
    public User convert(UserDto userDto) {
        User user = new User();
        if(!ObjectUtils.isEmpty(userDto)) {
            BeanUtils.copyProperties(userDto, user);
        }
        return user;
    }

    public UserDto convert(User user) {
        UserDto userDto = new UserDto();
        if(!ObjectUtils.isEmpty(user)) {
            BeanUtils.copyProperties(user, userDto);
        }
        return userDto;
    }

    public CourseDto convert(Course course) {
        CourseDto courseDto = new CourseDto();
        if(!ObjectUtils.isEmpty(course)) {
            BeanUtils.copyProperties(course, courseDto);
            if(!ObjectUtils.isEmpty(course.getTeacher())) {
                UserDto teacherDto = new UserDto();
                BeanUtils.copyProperties(course.getTeacher(), teacherDto);
                courseDto.setTeacher(teacherDto);
            }
        }
        return courseDto;
    }

    public Course convert(CourseDto courseDto) {
        Course course = new Course();
        if(!ObjectUtils.isEmpty(courseDto)) {
            BeanUtils.copyProperties(courseDto, course);
            if(!ObjectUtils.isEmpty(courseDto.getTeacher())) {
                User teacher = new User();
                BeanUtils.copyProperties(courseDto.getTeacher(), teacher);
                course.setTeacher(teacher);
            }
        }
        return course;
    }
}
