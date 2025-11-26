package org.eustache.employemanagement.Controllers;

import org.eustache.employemanagement.Services.AttendanceService;
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
    private AttendanceService attendanceService;

    @PostMapping("/generate/{employeeId}/{year}/{month}")
    public ResponseEntity<Payroll> generatePayroll(@PathVariable Integer employeeId,
                                                   @PathVariable int year,
                                                   @PathVariable int month) {
        Payroll payroll = attendanceService.generateMonthlyPayroll(employeeId, year, month);
        if (payroll == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(payroll);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Payroll>> getPayrollsForEmployee(@PathVariable Integer employeeId) {
        List<Payroll> payrolls = attendanceService.getPayrollsForEmployee(employeeId);
        return ResponseEntity.ok(payrolls);
    }
}
