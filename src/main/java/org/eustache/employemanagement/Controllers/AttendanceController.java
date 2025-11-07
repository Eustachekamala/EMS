package org.eustache.employemanagement.Controllers;

import lombok.RequiredArgsConstructor;
import org.eustache.employemanagement.DTOs.Requests.AttendanceRequestDTO;
import org.eustache.employemanagement.DTOs.Responses.AttendanceResponseDTO;
import org.eustache.employemanagement.DTOs.Responses.EmployeeSummaryDTO;
import org.eustache.employemanagement.Services.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService service;

    /**
     * Mark attendance using an explicit request payload.
     */
    @PostMapping("/rfid")
    public ResponseEntity<AttendanceResponseDTO> markAttendance(@RequestBody AttendanceRequestDTO request) {
        // ⚠️ Fetch employee from DB here using employeeId
        // Now, simulate employee data
        EmployeeSummaryDTO employee = new EmployeeSummaryDTO(
                request.employeeId(),
                "John",
                "Doe",
                "john.doe@example.com",
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
        EmployeeSummaryDTO employee = new EmployeeSummaryDTO(
                1,
                "Jane",
                "Smith",
                "jane.smith@example.com",
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
