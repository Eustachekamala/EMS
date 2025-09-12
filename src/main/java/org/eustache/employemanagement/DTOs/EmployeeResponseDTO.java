package org.eustache.employemanagement.DTOs;

import java.time.LocalDate;

public record EmployeeResponseDTO(
         Integer id,
         String firstName,
         String lastName,
         String email,
         String phone,
         LocalDate hireDate,
         String gender,
         DepartmentDTO department,
         JobDTO job,
         AddressDTO address
) {
}
