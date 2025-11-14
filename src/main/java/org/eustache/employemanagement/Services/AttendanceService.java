package org.eustache.employemanagement.Services; // lowercase

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.eustache.employemanagement.DTOs.Responses.AttendanceResponseDTO;
import org.eustache.employemanagement.DTOs.Responses.EmployeeResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final Map<String, AttendanceResponseDTO> activeAttendances = new HashMap<>();
    @Getter
    private final List<AttendanceResponseDTO> history = new ArrayList<>();
    private int idCounter = 1;

    public synchronized AttendanceResponseDTO recordAttendance(EmployeeResponseDTO employee) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        String rfidTag = employee.rfidTag();

        if (!activeAttendances.containsKey(rfidTag)) {
            // Check-in
            AttendanceResponseDTO record = new AttendanceResponseDTO(
                    idCounter++,
                    today,
                    now,
                    null,
                    "CHECKED_IN",
                    employee
            );
            activeAttendances.put(rfidTag, record);
            return record;
        } else {
            // Check-out
            AttendanceResponseDTO checkInRecord = activeAttendances.remove(rfidTag);
            AttendanceResponseDTO record = new AttendanceResponseDTO(
                    checkInRecord.id(),
                    today,
                    checkInRecord.checkInTime(),
                    now,
                    "CHECKED_OUT",
                    checkInRecord.employee()
            );
            history.add(record);
            return record;
        }
    }
}
