package org.eustache.employemanagement.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attendanceId;
    @CreationTimestamp
    private LocalDate attendanceDate;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private String attendanceStatus;

    //Relationships
    // Many attendance belongs to one employee
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
}
