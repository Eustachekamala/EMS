package org.eustache.employemanagement.Mappers;

import org.eustache.employemanagement.DTOs.Requests.AttendanceRequestDTO;
import org.eustache.employemanagement.DTOs.Responses.AttendanceResponseDTO;
import org.eustache.employemanagement.models.Attendance;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AttendanceMapper {

    private final EmployeeMapper employeeMapper;

    // Convert AttendanceRequestDTO to Attendance entity
    public Attendance toEntity(AttendanceRequestDTO attendanceRequestDTO){
        if(attendanceRequestDTO == null) return null;
        Attendance attendance = new Attendance();
        attendance.setAttendanceDate(attendanceRequestDTO.attendanceDate());
        attendance.setAttendanceStatus(attendanceRequestDTO.attendanceStatus());
        attendance.setCheckOutTime(attendanceRequestDTO.checkOutTime());
        attendance.setCheckInTime(attendanceRequestDTO.checkInTime());
        return attendance;
    }

    // Convert Attendance entity to AttendanceResponseDTO
    public AttendanceResponseDTO toDTO(Attendance attendance){
        if(attendance == null) return null;
        return new AttendanceResponseDTO(
                attendance.getAttendanceId(),
                attendance.getAttendanceDate(),
                attendance.getCheckInTime(),
                attendance.getCheckOutTime(),
                attendance.getAttendanceStatus(),
                attendance.getEmployee() != null ? employeeMapper.toResponseDTO(attendance.getEmployee()) : null
        );
    }
}
