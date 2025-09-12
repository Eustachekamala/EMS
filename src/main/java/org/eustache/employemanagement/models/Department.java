package org.eustache.employemanagement.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer departmentId;
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false, unique = true)
    private DepartmentType name;

    //Relationships
    @OneToMany(mappedBy = "department")
    private List<Employee> employees = new ArrayList<>();
}
