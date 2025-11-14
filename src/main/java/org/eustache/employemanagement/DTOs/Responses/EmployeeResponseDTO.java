package org.eustache.employemanagement.DTOs.Responses;


import java.time.LocalDate;

public record EmployeeResponseDTO(
         Integer id,
         String firstName,
         String lastName,
         String email,
         String phone,
         LocalDate hireDate,
         String gender,
         String departmentName,
         String jobTitle,
         String rfidTag
) {
    public String getJobTitle(){
        return jobTitle;
    }

    public String getDepartmentName(){
        return departmentName;
    }
}
