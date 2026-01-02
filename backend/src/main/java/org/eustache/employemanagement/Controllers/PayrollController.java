package org.eustache.employemanagement.Controllers;

import org.eustache.employemanagement.DTOs.Responses.PayrollResponseDTO;
import org.eustache.employemanagement.Services.PayRollService;
import org.eustache.employemanagement.models.Payroll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("payroll")
@Tag(name = "Payroll Management", description = "APIs for generating and retrieving payrolls")
public class PayrollController {

    @Autowired
    private PayRollService payRollService;

    @PostMapping("/generate/{employeeId}/{year}/{month}")
    public ResponseEntity<Payroll> generatePayroll(@PathVariable Integer employeeId,
            @PathVariable int year,
            @PathVariable int month) {
        return ResponseEntity.ok(payRollService.generateMonthlyPayroll(employeeId, year, month));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<PayrollResponseDTO>> getPayrollsForEmployee(@PathVariable Integer employeeId) {
        return ResponseEntity.ok(payRollService.getPayrollsForEmployee(employeeId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<org.eustache.employemanagement.DTOs.Responses.PayrollHistoryDTO>> getAllPayrolls() {
        return ResponseEntity.ok(payRollService.getAllPayrolls());
    }
}
