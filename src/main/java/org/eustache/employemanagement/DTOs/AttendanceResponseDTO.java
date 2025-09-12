package org.eustache.employemanagement.DTOs;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceResponseDTO(
        Integer id,
        LocalDate attendanceDate,
        LocalTime checkInTime,
        LocalTime checkOutTime,
        String attendanceStatus,
        EmployeeSummaryDTO employee
) {
}
