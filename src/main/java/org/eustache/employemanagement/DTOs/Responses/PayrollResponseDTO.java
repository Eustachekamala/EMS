package org.eustache.employemanagement.DTOs.Responses;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PayrollResponseDTO(
      Integer id,
      EmployeeResponseDTO employee,
      BigDecimal salary,
      LocalDate paymentDate
) {
}
