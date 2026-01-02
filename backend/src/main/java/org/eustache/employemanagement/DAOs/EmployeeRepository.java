package org.eustache.employemanagement.DAOs;

import org.eustache.employemanagement.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByRfidTag(String rfidTag);
    Optional<Employee> findByFirstname(String firstname);
    Optional<Employee> findByLastname(String lastname);
}
