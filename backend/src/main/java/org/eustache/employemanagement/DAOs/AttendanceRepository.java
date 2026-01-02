package org.eustache.employemanagement.DAOs;

import org.eustache.employemanagement.models.Attendance;
import org.eustache.employemanagement.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    Optional<Attendance> findByEmployeeAndAttendanceDate(Employee employee, LocalDate date);

    List<Attendance> findAllByEmployeeAndAttendanceDateBetween(Employee employee, LocalDate start, LocalDate end);
}
