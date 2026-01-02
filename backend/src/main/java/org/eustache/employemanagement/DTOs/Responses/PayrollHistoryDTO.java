package org.eustache.employemanagement.DTOs.Responses;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PayrollHistoryDTO(
        Integer payrollId,
        String employeeName,
        String jobTitle,
        String departmentName,
        BigDecimal salary,
        LocalDate paymentDate,
        int year,
        int month) {
}
