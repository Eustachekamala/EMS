package org.eustache.employemanagement.DTOs;

import java.math.BigDecimal;

public record JobDTO(
        String title,
        String description,
        BigDecimal salary
) {
}
