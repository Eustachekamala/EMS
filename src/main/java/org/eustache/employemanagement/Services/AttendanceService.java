package org.eustache.employemanagement.Services;

import lombok.RequiredArgsConstructor;
import org.eustache.employemanagement.DAOs.AttendanceRepository;
import org.eustache.employemanagement.DAOs.EmployeeRepository;
import org.eustache.employemanagement.DTOs.Requests.AttendanceRequestDTO;
import org.eustache.employemanagement.DTOs.Responses.AttendanceResponseDTO;
import org.eustache.employemanagement.DTOs.Responses.EmployeeSummaryDTO;
import org.eustache.employemanagement.models.Attendance;
import org.eustache.employemanagement.models.Employee;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;

    public AttendanceResponseDTO recordAttendance(AttendanceRequestDTO dto){
        Employee employee = employeeRepository.findByRfidTag(dto.rfidTag())
                .orElseThrow(() -> new RuntimeException("Employee not found for RFID: " + dto.rfidTag()));

        LocalDate today = dto.attendanceDate() != null ? dto.attendanceDate() : LocalDate.now();
        LocalTime now = LocalTime.now();

        Attendance attendance = attendanceRepository.findByEmployeeAndAttendanceDate(employee, today)
                .orElseGet(() -> {
                    Attendance newAttendance = new Attendance();
                    newAttendance.setEmployee(employee);
                    newAttendance.setAttendanceDate(today);
                    newAttendance.setCheckInTime(now);
                    newAttendance.setAttendanceStatus("PRESENT");
                    return newAttendance;
                });

        // If already checked in, update checkout time
        if (attendance.getCheckInTime() != null && attendance.getCheckOutTime() == null) {
            attendance.setCheckOutTime(now);
        }

        Attendance saved = attendanceRepository.save(attendance);
        return new AttendanceResponseDTO(
                saved.getAttendanceId(),
                saved.getAttendanceDate(),
                saved.getCheckInTime(),
                saved.getCheckOutTime(),
                saved.getAttendanceStatus(),
                new EmployeeSummaryDTO(
                        employee.getEmployeeId(),
                        employee.getFirstname(),
                        employee.getLastname(),
                        employee.getEmail(),
                        employee.getRfidTag())
        );
    }
}
