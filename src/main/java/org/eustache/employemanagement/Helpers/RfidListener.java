package org.eustache.employemanagement.Helpers;

import com.fazecast.jSerialComm.SerialPort;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.eustache.employemanagement.DTOs.Responses.AttendanceResponseDTO;
import org.eustache.employemanagement.DTOs.Responses.EmployeeResponseDTO;
import org.eustache.employemanagement.Services.AttendanceService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class RfidListener {
    private final AttendanceService attendanceService;

    @PostConstruct
    public void init() {
        SerialPort[] ports = SerialPort.getCommPorts();

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

                        // ⚠️ For now, simulate employee lookup
                        EmployeeResponseDTO employee = new EmployeeResponseDTO(
                            1,
                            "Jane",
                            "Smith",
                            "jane.smith@example.com",
                            "5555555555",
                            LocalDate.now(),
                            "Female",
                            "Product Management",
                            "Product Manager",
                            rfidTag
                    );

                        AttendanceResponseDTO result = attendanceService.recordAttendance(employee);
                        System.out.println("RFID SCAN -> " + result);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
