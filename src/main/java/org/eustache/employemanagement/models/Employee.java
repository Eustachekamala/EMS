package org.eustache.employemanagement.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phone;
    private LocalDate hireDate;
    private LocalDate birthDate;
    private String gender;
    private String position;
    private BigDecimal salary;

    @Column(unique = true)
    private String rfidTag;

    //Relationships
    @ManyToOne
    @JoinColumn(name = "department_id") // FK of department
    private Department department;

    @ManyToOne
    @JoinColumn(name = "job_id") // FK of Job
    private Job job;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendance> attendances;

    @OneToOne
    @JoinColumn(name = "address_id") // FK of address
    private Address address;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payroll> payrolls;
}
