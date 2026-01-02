package org.eustache.employemanagement.Mappers;

import org.eustache.employemanagement.DTOs.Requests.EmployeeRequestDTO;
import org.eustache.employemanagement.DTOs.Responses.EmployeeResponseDTO;
import org.eustache.employemanagement.models.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public Employee toEntity(EmployeeRequestDTO employeeRequestDTO) {
        if (employeeRequestDTO == null)
            return null;
        Employee employee = new Employee();
        employee.setFirstname(employeeRequestDTO.firstname());
        employee.setLastname(employeeRequestDTO.lastname());
        employee.setEmail(employeeRequestDTO.email());
        employee.setPhone(employeeRequestDTO.phone());
        employee.setBirthDate(employeeRequestDTO.birthDate());

        employee.setHireDate(employeeRequestDTO.hireDate());
        employee.setGender(employeeRequestDTO.gender());
        employee.setCity(employeeRequestDTO.city());
        employee.setStreet(employeeRequestDTO.street());
        employee.setCountry(employeeRequestDTO.country());
        employee.setZipcode(employeeRequestDTO.zipcode());

        employee.setPassword(employeeRequestDTO.password());
        employee.setRfidTag(employeeRequestDTO.rfidTag());
        employee.setDailyRate(employeeRequestDTO.dailyRate());
        return employee;
    }

    public EmployeeResponseDTO toResponseDTO(Employee employee) {
        if (employee == null)
            return null;
        return new EmployeeResponseDTO(
                employee.getId(),
                employee.getFirstname(),
                employee.getLastname(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getHireDate(),
                employee.getBirthDate(),
                employee.getCountry(),
                employee.getCity(),
                employee.getZipcode(),
                employee.getStreet(),
                employee.getGender(),
                employee.getDepartment() != null ? employee.getDepartment().getName().toString() : null,
                employee.getDepartment() != null ? employee.getDepartment().getDepartmentId() : null,
                employee.getJob() != null ? employee.getJob().getTitle() : null,
                employee.getJob() != null ? employee.getJob().getJobId() : null,
                employee.getRfidTag());
    }
}
