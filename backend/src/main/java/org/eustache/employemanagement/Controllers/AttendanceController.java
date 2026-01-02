package org.eustache.employemanagement.Controllers;


import java.util.List;

import org.eustache.employemanagement.DTOs.Responses.AttendanceResponseDTO;
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

    @GetMapping("/history")
    public ResponseEntity<List<AttendanceResponseDTO>> getAttendanceHistory() {
        return ResponseEntity.ok(service.getHistory());
    }
}
