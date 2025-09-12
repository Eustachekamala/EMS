package org.eustache.employemanagement.DAOs;

import org.eustache.employemanagement.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
