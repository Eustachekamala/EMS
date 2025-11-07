package org.eustache.employemanagement.DAOs;

import java.util.Optional;

import org.eustache.employemanagement.models.Department;
import org.eustache.employemanagement.models.DepartmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Optional<Department> findByName(DepartmentType name);
}
