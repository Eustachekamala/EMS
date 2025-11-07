package org.eustache.employemanagement.DTOs.Requests;

import java.math.BigDecimal;

public record JobRequestDTO(
        String title,
        String description,
        BigDecimal salary
) {
}
