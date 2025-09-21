package org.eustache.employemanagement.Controllers;

import lombok.RequiredArgsConstructor;
import org.eustache.employemanagement.DTOs.Requests.EmployeeRequestDTO;
import org.eustache.employemanagement.DTOs.Responses.EmployeeSummaryDTO;
import org.eustache.employemanagement.Services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeSummaryDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("{id}")
    public ResponseEntity<EmployeeSummaryDTO> getEmployeeById(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertEmployee(@RequestBody EmployeeRequestDTO employee) {
        return ResponseEntity.ok(  employeeService.createEmployee(employee));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Integer id, @RequestBody EmployeeRequestDTO employee) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.deleteEmployee(id));
    }

    @GetMapping("/rfid/{rfid}")
    public ResponseEntity<String> getEmployeeByRfid(@PathVariable String rfid) {
        return ResponseEntity.ok(employeeService.getEmployeeByRfid(rfid));
    }

    @GetMapping("/search/{firstname}")
    public ResponseEntity<EmployeeSummaryDTO> getEmployeeByFirstname(@PathVariable String firstname) {
        return ResponseEntity.ok(employeeService.getEmployeeByFirstname(firstname));
    }

    @GetMapping("/search/lastname/{lastname}")
    public ResponseEntity<EmployeeSummaryDTO> getEmployeeByLastname(@PathVariable String lastname) {
        return ResponseEntity.ok(employeeService.getEmployeeByLastname(lastname));
    }
}
