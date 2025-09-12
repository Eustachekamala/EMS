package org.eustache.employemanagement.DTOs.Requests;

import java.time.LocalDate;

public record EmployeeRequestDTO(
         String firstName,
         String lastName,
         String email,
         String phone,
         LocalDate dob,
         String gender,
         Integer departmentId,
         Integer jobId,
         Integer addressId
) {
}
