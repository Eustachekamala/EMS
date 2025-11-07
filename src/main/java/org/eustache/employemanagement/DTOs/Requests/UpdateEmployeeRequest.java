package org.eustache.employemanagement.DTOs.Requests;

import java.time.LocalDate;

public record UpdateEmployeeRequest(
         String firstName,
         String lastName,
         String email,
         String phone,
         LocalDate dob,
         String gender,
         String position,
         LocalDate hireDate,
         String street,
         String city,
         String country,
         String zipcode,
         Integer departmentId,
         Integer jobId
) {

}
