package org.eustache.employemanagement.DTOs.Responses;

import org.eustache.employemanagement.DTOs.DepartmentDTO;
import org.eustache.employemanagement.DTOs.Requests.JobRequestDTO;

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
         JobRequestDTO job
) {
}
