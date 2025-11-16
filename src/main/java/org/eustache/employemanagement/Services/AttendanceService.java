package org.eustache.employemanagement.Services; // lowercase

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.eustache.employemanagement.DTOs.Responses.AttendanceResponseDTO;
import org.eustache.employemanagement.DTOs.Responses.EmployeeResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final Map<String, AttendanceResponseDTO> activeAttendances = new HashMap<>();

    @Getter
    private final List<AttendanceResponseDTO> history = new ArrayList<>();
    private int idCounter = 1;

    // This method helps to 
    public synchronized AttendanceResponseDTO recordAttendance(EmployeeResponseDTO employee) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        String rfidTag = employee.rfidTag();

        if (!activeAttendances.containsKey(rfidTag)) {
            // Check-in
            AttendanceResponseDTO record = new AttendanceResponseDTO(
                    idCounter++,
                    today,
                    now,
                    null,
                    "CHECKED_IN",
                    employee
            );
            activeAttendances.put(rfidTag, record);
            return record;
        } else {
            // Check-out
            AttendanceResponseDTO checkInRecord = activeAttendances.remove(rfidTag);
            AttendanceResponseDTO record = new AttendanceResponseDTO(
                    checkInRecord.id(),
                    today,
                    checkInRecord.checkInTime(),
                    now,
                    "CHECKED_OUT",
                    checkInRecord.employee()
            );
            history.add(record);
            return record;
        }
    }
}
