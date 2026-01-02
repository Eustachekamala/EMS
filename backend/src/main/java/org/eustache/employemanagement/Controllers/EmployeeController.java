package org.eustache.employemanagement.Controllers;

import org.eustache.employemanagement.DTOs.Requests.EmployeeRequestDTO;
import org.eustache.employemanagement.DTOs.Requests.UpdateEmployeeRequestDTO;
import org.eustache.employemanagement.DTOs.Responses.AttendanceResponseDTO;
import org.eustache.employemanagement.DTOs.Responses.EmployeeResponseDTO;
import org.eustache.employemanagement.Exceptions.NotFoundException;
import org.eustache.employemanagement.Services.AttendanceService;
import org.eustache.employemanagement.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("employees")
@Tag(
    name = "Employee Management",
    description = "APIs for managing employees within the Employee Management System, including creation, retrieval, updating, and deletion of employee records."
)
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertEmployee(@RequestBody EmployeeRequestDTO employee) {
        return ResponseEntity.ok(  employeeService.createEmployee(employee));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Integer id, @RequestBody UpdateEmployeeRequestDTO employee) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.deleteEmployee(id));
    }

    @GetMapping("/rfid/{rfid}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeByRfid(@PathVariable String rfid) {
        return ResponseEntity.ok(employeeService.getEmployeeByRfid(rfid));
    }

    @PostMapping("/scan")
    public ResponseEntity<?> scanRfid(@RequestBody Map<String, String> body) {
        String rfid = body.get("uid");
        if (rfid == null || rfid.isBlank()) {
            return ResponseEntity.badRequest().body("RFID cannot be empty");
        }

        // Get employee info as DTO
        EmployeeResponseDTO employeeDto;
        try {
            employeeDto = employeeService.getEmployeeByRfid(rfid);
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

        // Record attendance
        AttendanceResponseDTO attendanceDto = attendanceService.recordAttendance(employeeDto);

        return ResponseEntity.ok(attendanceDto);
    }
    

    @GetMapping("/search/{firstname}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeByFirstname(@PathVariable String firstname) {
        return ResponseEntity.ok(employeeService.getEmployeeByFirstname(firstname));
    }

    @GetMapping("/search/lastname/{lastname}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeByLastname(@PathVariable String lastname) {
        return ResponseEntity.ok(employeeService.getEmployeeByLastname(lastname));
    }
}
