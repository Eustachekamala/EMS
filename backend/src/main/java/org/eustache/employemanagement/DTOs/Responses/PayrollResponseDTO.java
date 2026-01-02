package org.eustache.employemanagement.DTOs.Responses;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PayrollResponseDTO(
      BigDecimal salary,
      LocalDate paymentDate,
      int payrollYear,
      int payrollMonth
) {
}
