package org.eustache.employemanagement.Services;

import lombok.RequiredArgsConstructor;

import org.eustache.employemanagement.DTOs.Responses.AttendanceResponseDTO;
import org.eustache.employemanagement.DTOs.Responses.EmployeeResponseDTO;
import org.eustache.employemanagement.DAOs.AttendanceRepository;
import org.eustache.employemanagement.DAOs.EmployeeRepository;
import org.eustache.employemanagement.Mappers.AttendanceMapper;
import org.eustache.employemanagement.models.Attendance;
import org.eustache.employemanagement.models.Employee;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Records an attendance event for a given employee. This method toggles between
 * check-in and check-out behavior based on whether there is an active attendance
 * record for the employee's RFID tag.
 *
 * - Check-in:
 *   - If there is no active attendance entry for employee.rfidTag(), a new attendance
 *     is created with a unique id (incremented idCounter), the current local date
 *     and time for check-in, a null check-out time, and status "CHECKED_IN".
 *   - The new record is stored in activeAttendances keyed by the employee RFID.
 *   - The created AttendanceResponseDTO is returned.
 *
 * - Check-out:
 *   - If an active attendance exists for employee.rfidTag(), the method treats the
 *     invocation as a check-out: it removes the active record, creates a new record
 *     reusing the original id and check-in time but setting the current local time
 *     as check-out, sets status to "CHECKED_OUT", adds the finalized record to
 *     history, and returns it.
 *
 * Thread-safety and side effects:
 * - The method is synchronized to ensure atomic updates to activeAttendances, history,
 *   and idCounter.
 * - Uses LocalDate.now() and LocalTime.now() for timestamps (server local clock).
 * - Mutates activeAttendances and history; idCounter is incremented on check-in.
 * - Calls employee.rfidTag() to identify the employee; if the RFID is null or if
 *   multiple employees share the same RFID, behavior may be incorrect.
 *
 * Parameters:
 * @param employee the EmployeeResponseDTO for whom to record attendance; expected
 *                 to be non-null and have a non-null RFID tag.
 *
 * Returns:
 * @return an AttendanceResponseDTO representing the new check-in or the completed
 *         check-out record.
 *
 * Exceptions:
 * - May throw NullPointerException if employee or employee.rfidTag() is null.
 */
@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final AttendanceMapper attendanceMapper;

    private final List<AttendanceResponseDTO> history = List.of();
    /**
     * Record an attendance event for the given employee DTO.
     * If no attendance exists for today, this creates a check-in.
     * If a check-in exists without a check-out, this performs check-out and persists the entry.
     */
    public synchronized AttendanceResponseDTO recordAttendance(EmployeeResponseDTO employeeDto) {
        if (employeeDto == null || employeeDto.rfidTag() == null) return null;

        String rfid = employeeDto.rfidTag().trim();
        Employee employee = employeeRepository.findByRfidTag(rfid).orElse(null);
        if (employee == null) {
            System.out.println("⚠️ No employee found for RFID: " + rfid);
            return null;
        }

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        Attendance existing = attendanceRepository.findByEmployeeAndAttendanceDate(employee, today).orElse(null);
        if (existing == null) {
            // create check-in
            Attendance attendance = new Attendance();
            attendance.setEmployee(employee);
            attendance.setCheckInTime(now);
            attendance.setAttendanceStatus("CHECKED_IN");
            Attendance saved = attendanceRepository.save(attendance);
            return attendanceMapper.toDTO(saved);
        }

        // If check-out not yet recorded -> check-out
        if (existing.getCheckOutTime() == null) {
            existing.setCheckOutTime(now);
            existing.setAttendanceStatus("CHECKED_OUT");
            Attendance saved = attendanceRepository.save(existing);

            // compute duration and log it
            try {
                LocalTime in = saved.getCheckInTime();
                LocalTime out = saved.getCheckOutTime();
                if (in != null && out != null) {
                    Duration d = Duration.between(in, out);
                    long minutes = d.toMinutes();
                    System.out.println("Attendance for employee " + employee.getId() + " duration: " + minutes + " minutes.");
                }
            } catch (Exception ignored) {}

            return attendanceMapper.toDTO(saved);
        }

        // already checked out today -> create a new check-in (start a new attendance entry)
        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setCheckInTime(now);
        attendance.setAttendanceStatus("CHECKED_IN");
        Attendance saved = attendanceRepository.save(attendance);
        return attendanceMapper.toDTO(saved);
    }

    /**
     * Return the history of attendances for the given employee.
     */
    public List<AttendanceResponseDTO> getHistory(){
        return new ArrayList<>(history);
    }
}
