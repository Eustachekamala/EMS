package org.eustache.employemanagement.DTOs;

import java.math.BigDecimal;

public record JobDTO(
        Integer id,
        String title,
        String description,
        BigDecimal minSalary,
        BigDecimal maxSalary
) {
}
