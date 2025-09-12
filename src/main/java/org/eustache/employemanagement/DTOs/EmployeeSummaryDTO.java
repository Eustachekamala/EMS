package org.eustache.employemanagement.DTOs;

public record EmployeeSummaryDTO(
        Integer id,
        String firstname,
        String lastname,
        String email
) {
}
