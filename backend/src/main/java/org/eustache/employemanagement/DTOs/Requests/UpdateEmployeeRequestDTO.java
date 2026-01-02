package org.eustache.employemanagement.DTOs.Requests;

import java.time.LocalDate;

public record UpdateEmployeeRequestDTO(
        String firstname,
        String lastname,
        String email,
        String phone,
        LocalDate dob,
        String gender,
        LocalDate hireDate,
        String country,
        String city,
        String zipcode,
        String street,
        Integer departmentId,
        Integer jobId) {

}
