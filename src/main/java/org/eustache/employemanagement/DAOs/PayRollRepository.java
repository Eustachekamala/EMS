package org.eustache.employemanagement.DAOs;

import org.eustache.employemanagement.models.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayRollRepository extends JpaRepository<Payroll, Integer> {
}
