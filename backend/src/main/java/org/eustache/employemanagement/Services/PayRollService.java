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
     * Generate and persist a payroll for the given employee for the specified
     * month.
     * Salary is computed based on Job salary (if available) or Employee daily rate.
     */
    public Payroll generateMonthlyPayroll(Integer employeeId, int year, int month) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found with id: " + employeeId));
        if (employee == null)
            return null;

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        // Check if the payroll already exists
        boolean exists = payRollRepository.existsByEmployeeAndPaymentDateBetween(employee, start, end);
        if (exists) {
            // Return existing
            return payRollRepository.findByEmployeeAndPayrollYearAndPayrollMonth(employee, year, month)
                    .stream()
                    .findFirst()
                    .orElse(null);
        }

        List<Attendance> attendances = attendanceRepository.findAllByEmployeeAndAttendanceDateBetween(employee, start,
                end);
        long daysAttended = attendances.stream().filter(a -> a.getCheckOutTime() != null)
                .map(Attendance::getAttendanceDate).distinct().count();

        BigDecimal dailyRate = BigDecimal.ZERO;

        // Prioritize Job Salary (Monthly / 30)
        if (employee.getJob() != null && employee.getJob().getSalary() != null) {
            dailyRate = employee.getJob().getSalary().divide(BigDecimal.valueOf(30), 2, java.math.RoundingMode.HALF_UP);
        } else if (employee.getDailyRate() != null) {
            dailyRate = employee.getDailyRate();
        }

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
        if (employee == null)
            return List.of();
        return payRollRepository.findAllByEmployee(employee).stream()
                .map(payroll -> new PayrollResponseDTO(payroll.getSalary(), payroll.getPaymentDate(),
                        payroll.getPayrollYear(), payroll.getPayrollMonth()))
                .toList();
    }

    /**
     * Return all payroll history global.
     */
    public List<org.eustache.employemanagement.DTOs.Responses.PayrollHistoryDTO> getAllPayrolls() {
        return payRollRepository.findAll().stream()
                .map(p -> new org.eustache.employemanagement.DTOs.Responses.PayrollHistoryDTO(
                        p.getPayrollId(),
                        p.getEmployee().getFirstname() + " " + p.getEmployee().getLastname(),
                        p.getEmployee().getJob() != null ? p.getEmployee().getJob().getTitle() : "N/A",
                        p.getEmployee().getDepartment() != null ? p.getEmployee().getDepartment().getName().name()
                                : "N/A",
                        p.getSalary(),
                        p.getPaymentDate(),
                        p.getPayrollYear(),
                        p.getPayrollMonth()))
                .toList();
    }
}
