package org.eustache.employemanagement.Controllers;

import lombok.RequiredArgsConstructor;
import org.eustache.employemanagement.DTOs.Requests.AttendanceRequestDTO;
import org.eustache.employemanagement.DTOs.Responses.AttendanceResponseDTO;
import org.eustache.employemanagement.Services.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService  attendanceService;

    @PostMapping("/rfid")
    public ResponseEntity<AttendanceResponseDTO> markAttendance(@RequestBody AttendanceRequestDTO attendanceRequestDTO) {
        return ResponseEntity.ok().body(attendanceService.recordAttendance(attendanceRequestDTO));
    }

    // ✅ Simulation endpoint: alternates check-in / check-out
    @PostMapping("/simulate/{rfidTag}")
    public ResponseEntity<AttendanceResponseDTO> simulateScan(@PathVariable String rfidTag) {
        AttendanceRequestDTO dto = new AttendanceRequestDTO(
                rfidTag,
                null, // auto set date in service
                null,
                null,
                null
        );
        AttendanceResponseDTO response = attendanceService.recordAttendance(dto);
        return ResponseEntity.ok(response);
    }
}
