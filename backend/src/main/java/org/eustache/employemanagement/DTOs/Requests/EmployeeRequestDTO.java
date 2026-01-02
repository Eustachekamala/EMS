package org.eustache.employemanagement.DTOs.Requests;

import java.time.LocalDate;

public record EmployeeRequestDTO(
        String firstname,
        String lastname,
        String email,
        String phone,
        String password,
        LocalDate dob,
        String gender,
        LocalDate hireDate,
        String country,
        String city,
        String zipcode,
        String street,
        String rfidTag,
        java.math.BigDecimal dailyRate,
        Integer departmentId,
        Integer jobId) {
}
