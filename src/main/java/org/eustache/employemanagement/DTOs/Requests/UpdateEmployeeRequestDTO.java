package org.eustache.employemanagement.DTOs.Requests;

import java.time.LocalDate;

public record UpdateEmployeeRequestDTO(
         String firstName,
         String lastName,
         String email,
         String phone,
         LocalDate dob,
         String gender,
         String position,
         LocalDate hireDate,
         String country,
         String city,
         String zipcode,
         String street,
         Integer departmentId,
         Integer jobId
) {

}
