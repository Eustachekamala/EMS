package org.eustache.employemanagement.DTOs.Responses;

import java.time.LocalDate;

public record EmployeeResponseDTO(
        Integer id,
        String firstname,
        String lastname,
        String email,
        String phone,
        LocalDate hireDate,
        LocalDate birthDate,
        String country,
        String city,
        String zipcode,
        String street,
        String gender,
        String departmentName,
        Integer departmentId,
        String jobTitle,
        Integer jobId,
        String rfidTag) {
    public String getJobTitle() {
        return jobTitle;
    }

    public String getDepartmentName() {
        return departmentName;
    }
}
