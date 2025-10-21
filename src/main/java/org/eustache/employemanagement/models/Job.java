package org.eustache.employemanagement.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String title;
    private String description;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;

    //Relationships
    @OneToMany(mappedBy = "job")
    private List<Employee> employees;
}
