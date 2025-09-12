package org.eustache.employemanagement.DTOs;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PayrollResponseDTO(
      Integer id,
      EmployeeSummaryDTO employee,
      BigDecimal salary,
      LocalDate paymentDate
) {
}
