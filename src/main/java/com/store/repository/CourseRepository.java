package com.store.repository;

import com.store.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;


public interface CourseRepository extends JpaRepository<Course, UUID> {
}

