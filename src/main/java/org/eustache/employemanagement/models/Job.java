package org.eustache.employemanagement.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer jobId;
    @Column(nullable = false, unique = true)
    private String title;
    private String description;
    private BigDecimal salary;

    // One job can be assigned to many employees
    @OneToMany(mappedBy = "job")
    private List<Employee> employees;

    // Many jobs can belong to one department
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
}
