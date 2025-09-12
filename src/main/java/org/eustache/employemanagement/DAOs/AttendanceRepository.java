package org.eustache.employemanagement.DAOs;

import org.eustache.employemanagement.models.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
}
