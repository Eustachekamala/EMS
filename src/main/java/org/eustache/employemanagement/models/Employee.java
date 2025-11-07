package org.eustache.employemanagement.models;

import jakarta.persistence.*;
import lombok.Data;

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
    @Column(unique = true)
    private String email;
    private String password;
    @Column(unique = true)
    private String phone;
    private LocalDate hireDate;
    private LocalDate birthDate;
    private String gender;
    private String position;
    private String street;
    private String city;
    private String country;
    private String zipcode;

    @Column(unique = true)
    private String rfidTag;

    //Relationships
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = true) // FK of department
    private Department department;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = true) // FK of Job
    private Job job;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendance> attendances;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payroll> payrolls;
}
