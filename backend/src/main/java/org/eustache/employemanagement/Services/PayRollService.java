package org.eustache.employemanagement.Services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.eustache.employemanagement.DAOs.AttendanceRepository;
import org.eustache.employemanagement.DAOs.EmployeeRepository;
import org.eustache.employemanagement.DAOs.PayRollRepository;
import org.eustache.employemanagement.DTOs.Responses.PayrollResponseDTO;
import org.eustache.employemanagement.Exceptions.NotFoundException;
import org.eustache.employemanagement.models.Attendance;
import org.eustache.employemanagement.models.Employee;
import org.eustache.employemanagement.models.Payroll;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayRollService {
    private final PayRollRepository payRollRepository;
    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    /**
     * Generate and persist a payroll for the given employee for the specified month.
     * Salary is computed as dailyRate * daysAttended (days with a recorded check-out).
     */
    public Payroll generateMonthlyPayroll(Integer employeeId, int year, int month) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found with id: " + employeeId));
        if (employee == null) return null;

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        // Check if the payroll already exists
        boolean exists = payRollRepository.existsByEmployeeAndPaymentDateBetween(employee, start, end);
        if(exists){
            // Optionally, you can throw an exception or just return the existing payroll
            return payRollRepository.findByEmployeeAndPayrollYearAndPayrollMonth(employee, year, month)
                    .stream()
                    .findFirst()
                    .orElse(null);
        }

        List<Attendance> attendances = attendanceRepository.findAllByEmployeeAndAttendanceDateBetween(employee, start, end);
        long daysAttended = attendances.stream().filter(a -> a.getCheckOutTime() != null).map(Attendance::getAttendanceDate).distinct().count();

        BigDecimal dailyRate = employee.getDailyRate() != null ? employee.getDailyRate() : BigDecimal.ZERO;
        BigDecimal salary = dailyRate.multiply(BigDecimal.valueOf(daysAttended));

        Payroll payroll = new Payroll();
        payroll.setEmployee(employee);
        payroll.setSalary(salary);
        payroll.setPayrollYear(year);
        payroll.setPayrollMonth(month);
        return payRollRepository.save(payroll);
    }

    /**
     * Return all payrolls for the given employee.
     */
    public List<PayrollResponseDTO> getPayrollsForEmployee(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) return List.of();
        return payRollRepository.findAllByEmployee(employee).stream().map(payroll -> new PayrollResponseDTO(payroll.getSalary(), payroll.getPaymentDate(), payroll.getPayrollYear(), payroll.getPayrollMonth())).toList();
    }
}
