package com.store.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity @Table
public class Teacher {
    @Column
    private String subject;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "")
    private List<Course> courses;
}
