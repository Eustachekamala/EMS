package org.eustache.employemanagement.DAOs;

import org.eustache.employemanagement.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
