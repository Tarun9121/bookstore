package com.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name="enrollments")
public class Enrollment {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type="uuid-char")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(36)")
    private Status enrollmentStatus = Status.ENROLLED;

    private boolean isDeleted = false;

    public enum Status {
        ENROLLED, COMPLETED, DROPPED
    }
}
