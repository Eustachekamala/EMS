package org.eustache.employemanagement.DTOs.Responses;

public record EmployeeSummaryDTO(
        Integer id,
        String firstname,
        String lastname,
        String email
) {
}
