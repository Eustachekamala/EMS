package org.eustache.employemanagement.DAOs;

import org.eustache.employemanagement.models.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.eustache.employemanagement.models.Employee;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PayRollRepository extends JpaRepository<Payroll, Integer> {
	boolean existsByEmployeeAndPaymentDateBetween(Employee employee, LocalDate start, LocalDate end);
	List<Payroll> findByEmployeeAndPayrollYearAndPayrollMonth(Employee employee, int year, int month);
	List<Payroll> findAllByEmployee(Employee employee);
}
