package org.eustache.employemanagement.DTOs.Requests;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeRequestDTO(
        String firstname,
        String lastname,
        String email,
        String phone,
        String password,
        LocalDate birthDate,
        String gender,
        LocalDate hireDate,
        String country,
        String city,
        String zipcode,
        String street,
        String rfidTag,
        BigDecimal dailyRate,
        Integer departmentId,
        Integer jobId) {
}
