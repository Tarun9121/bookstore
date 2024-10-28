package com.store.repository;

import com.store.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    Optional<Enrollment> findByStudentIdAndCourseIdAndIsDeletedFalse(UUID studentId, UUID courseId);
    List<Enrollment> findAllByStudentIdAndIsDeletedFalse(UUID studentId);
}
