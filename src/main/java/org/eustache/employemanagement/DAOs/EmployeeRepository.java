package org.eustache.employemanagement.DAOs;

import org.eustache.employemanagement.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.ScopedValue;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByRfidTag(String rfidTag);
}
