package org.eustache.employemanagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer departmentId;
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false, unique = true)
    private DepartmentType name;

    // One department can have many jobs
    @OneToMany(mappedBy = "department")
    private List<Job> jobs = new ArrayList<>();

    // One department can have many employees
    @OneToMany(mappedBy = "department")
    private List<Employee> employees = new ArrayList<>();
}
