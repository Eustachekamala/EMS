package org.eustache.employemanagement.Services;

import lombok.RequiredArgsConstructor;
import org.eustache.employemanagement.DAOs.EmployeeRepository;
import org.eustache.employemanagement.DTOs.Requests.EmployeeRequestDTO;
import org.eustache.employemanagement.DTOs.Responses.EmployeeSummaryDTO;
import org.eustache.employemanagement.Exceptions.NotFoundException;
import org.eustache.employemanagement.Mappers.EmployeeMapper;
import org.eustache.employemanagement.models.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper;

    public String createEmployee(EmployeeRequestDTO employeerequestDTO) {
            Employee employee = employeeMapper.toEntity(employeerequestDTO);
            employeeRepository.save(employee);
            return "Employee created successfully";
    }
}
