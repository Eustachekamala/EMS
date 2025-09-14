package org.eustache.employemanagement.Mappers;

import org.eustache.employemanagement.DTOs.Requests.EmployeeRequestDTO;
import org.eustache.employemanagement.DTOs.Responses.EmployeeSummaryDTO;
import org.eustache.employemanagement.models.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public Employee toEntity(EmployeeRequestDTO employeeRequestDTO) {
        if (employeeRequestDTO == null) return null;
        Employee employee = new Employee();
        employee.setFirstname(employeeRequestDTO.firstName());
        employee.setLastname(employeeRequestDTO.lastName());
        employee.setEmail(employeeRequestDTO.email());
        employee.setPhone(employeeRequestDTO.phone());
        employee.setBirthDate(employeeRequestDTO.dob());
        employee.setGender(employeeRequestDTO.gender());
        return employee;
    }

    public EmployeeSummaryDTO toEmployeeSummaryDTO(Employee employee) {
        if (employee == null) return null;
        return new EmployeeSummaryDTO(
                employee.getEmployeeId(),
                employee.getFirstname(),
                employee.getLastname(),
                employee.getEmail(),
                employee.getRfidTag()
        );
    }
}
