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

    public List<EmployeeSummaryDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employeeMapper::toEmployeeSummaryDTO)
                .toList();
    }

    public EmployeeSummaryDTO getEmployeeById(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found with id: " + id));
        return employeeMapper.toEmployeeSummaryDTO(employee);
    }

    public String deleteEmployee(Integer id) {
        if (!employeeRepository.existsById(id)) {
            throw new NotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
        return "Employee deleted successfully";
    }

    public String updateEmployee(Integer id, EmployeeRequestDTO employeerequestDTO) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found with id: " + id));
        existingEmployee.setFirstname(employeerequestDTO.firstName());
        existingEmployee.setLastname(employeerequestDTO.lastName());
        existingEmployee.setEmail(employeerequestDTO.email());
        existingEmployee.setPhone(employeerequestDTO.phone());
        existingEmployee.setBirthDate(employeerequestDTO.dob());
        existingEmployee.setGender(employeerequestDTO.gender());
        employeeRepository.save(existingEmployee);
        return "Employee updated successfully";
    }
}
