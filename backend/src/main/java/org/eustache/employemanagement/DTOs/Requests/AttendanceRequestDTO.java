package org.eustache.employemanagement.DTOs.Requests;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceRequestDTO(
        String rfidTag,
        Integer employeeId,
        LocalDate attendanceDate,
        LocalTime checkInTime,
        LocalTime checkOutTime,
        String attendanceStatus
) {
}
