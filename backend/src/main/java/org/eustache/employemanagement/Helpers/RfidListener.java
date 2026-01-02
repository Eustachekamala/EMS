package org.eustache.employemanagement.Helpers;

import com.fazecast.jSerialComm.SerialPort;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.eustache.employemanagement.DTOs.Responses.AttendanceResponseDTO;
import org.eustache.employemanagement.DTOs.Responses.EmployeeResponseDTO;
import org.eustache.employemanagement.Services.AttendanceService;
import org.eustache.employemanagement.DAOs.EmployeeRepository;
import org.eustache.employemanagement.Mappers.EmployeeMapper;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class RfidListener {
    private final AttendanceService attendanceService;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @PostConstruct
    public void init() {
        SerialPort[] ports = SerialPort.getCommPorts();

        //This Condition statement check if there's a RFID connected
        if (ports.length == 0) {
            System.err.println("⚠️ No serial ports detected. RFID listener will not start.");
            return; // safely exit without throwing
        }

        SerialPort comPort = ports[0]; // use the first available port
        comPort.setBaudRate(9600);

        if (!comPort.openPort()) {
            System.err.println("⚠️ Failed to open serial port: " + comPort.getSystemPortName());
            return;
        }

        System.out.println("✅ RFID listener started on port: " + comPort.getSystemPortName());

        new Thread(() -> {
            StringBuilder buffer = new StringBuilder();
            while (true) {
                try {
                    while (comPort.bytesAvailable() == 0) Thread.sleep(50);

                    byte[] readBuffer = new byte[comPort.bytesAvailable()];
                    comPort.readBytes(readBuffer, readBuffer.length);

                    String data = new String(readBuffer, StandardCharsets.UTF_8).trim();
                    buffer.append(data);

                    if (data.contains("\n")) { // assuming reader sends newline
                        String rfidTag = buffer.toString().trim();
                        buffer.setLength(0);

                        // Lookup employee by RFID from DB
                        try {
                            if (rfidTag == null || rfidTag.isBlank()) {
                                System.out.println("⚠️ Received empty RFID tag, skipping.");
                                continue;
                            }

                            employeeRepository.findByRfidTag(rfidTag.trim())
                                .ifPresentOrElse(emp -> {
                                    EmployeeResponseDTO dto = employeeMapper.toResponseDTO(emp);
                                    AttendanceResponseDTO result = attendanceService.recordAttendance(dto);
                                    System.out.println("RFID SCAN -> " + result);
                                }, () -> {
                                    System.out.println("⚠️ No employee found for RFID: " + rfidTag);
                                });
                        } catch (Exception ex) {
                            System.err.println("Error processing RFID '" + rfidTag + "': " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
