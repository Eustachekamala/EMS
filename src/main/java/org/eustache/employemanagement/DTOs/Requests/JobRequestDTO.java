package org.eustache.employemanagement.DTOs.Requests;

import java.math.BigDecimal;

import org.eustache.employemanagement.models.DepartmentType;

public record JobRequestDTO(
        String title,
        String description,
        BigDecimal salary,
        DepartmentType departmentName
) {
}
