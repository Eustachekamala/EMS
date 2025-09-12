package org.eustache.employemanagement.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class Job {
    @Id
    private Integer jobId;
    private String title;
    private String description;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;

    //Relationships
    @OneToMany(mappedBy = "job")
    private List<Employee> employees;
}
