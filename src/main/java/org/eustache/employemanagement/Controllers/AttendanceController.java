package org.eustache.employemanagement.Controllers;

import java.time.LocalDate;

import org.eustache.employemanagement.DTOs.Requests.AttendanceRequestDTO;
import org.eustache.employemanagement.DTOs.Responses.AttendanceResponseDTO;
import org.eustache.employemanagement.DTOs.Responses.EmployeeResponseDTO;
import org.eustache.employemanagement.Services.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("attendance")
@Tag(
    name = "Attendance Management",
    description = "APIs for managing employee attendance within the Employee Management System, including marking attendance and retrieving attendance history."
)
public class AttendanceController {

    @Autowired
    private AttendanceService service;

    /**
     * Mark attendance using an explicit request payload.
     */
    @PostMapping("/rfid")
    public ResponseEntity<AttendanceResponseDTO> markAttendance(@RequestBody AttendanceRequestDTO request) {
        // ⚠️ Fetch employee from DB here using employeeId
        // Now, simulate employee data
        EmployeeResponseDTO employee = new EmployeeResponseDTO(
                request.employeeId(),
                "John",
                "Doe",
                "john.doe@example.com",
                "1234567890",
                LocalDate.now(),
                "Male",
                "Engineering",
                "Software Engineer",
                "RFID12345"
        );

        AttendanceResponseDTO response = service.recordAttendance(employee);
        return ResponseEntity.ok(response);
    }

    /**
     * Simulate a scan with only RFID tag (no full request).
     * Automatically alternates between check-in and check-out.
     */
    @PostMapping("/simulate/{rfidTag}")
    public ResponseEntity<AttendanceResponseDTO> simulateScan(@PathVariable String rfidTag) {
        // ⚠️ In real use: lookup employee by RFID in EmployeeRepository
        EmployeeResponseDTO employee = new EmployeeResponseDTO(
                1,
                "Jane",
                "Smith",
                "jane.smith@example.com",
                "5555555555",
                LocalDate.now(),
                "Female",
                "Product Management",
                "Product Manager",
                rfidTag
        );

        AttendanceResponseDTO response = service.recordAttendance(employee);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all attendance history.
     */
    @GetMapping("/history")
    public ResponseEntity<?> getHistory() {
        return ResponseEntity.ok(service.getHistory());
    }
}
