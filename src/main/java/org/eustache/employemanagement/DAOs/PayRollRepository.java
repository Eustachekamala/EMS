package org.eustache.employemanagement.DAOs;

import org.eustache.employemanagement.models.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.eustache.employemanagement.models.Employee;
import java.util.List;

@Repository
public interface PayRollRepository extends JpaRepository<Payroll, Integer> {
	List<Payroll> findAllByEmployee(Employee employee);
}
