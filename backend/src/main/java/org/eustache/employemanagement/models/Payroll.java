package org.eustache.employemanagement.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer payrollId;
    private BigDecimal salary;
    @CreationTimestamp
    private LocalDate paymentDate;
    private int payrollYear;
    private int payrollMonth;


    // Relationship
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
