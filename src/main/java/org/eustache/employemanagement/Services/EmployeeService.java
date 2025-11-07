package org.eustache.employemanagement.Services;

import org.eustache.employemanagement.DAOs.DepartmentRepository;
import org.eustache.employemanagement.DAOs.EmployeeRepository;
import org.eustache.employemanagement.DAOs.JobRepository;
import org.eustache.employemanagement.DTOs.Requests.EmployeeRequestDTO;
import org.eustache.employemanagement.DTOs.Requests.UpdateEmployeeRequest;
import org.eustache.employemanagement.DTOs.Responses.EmployeeSummaryDTO;
import org.eustache.employemanagement.Exceptions.NotFoundException;
import org.eustache.employemanagement.Mappers.EmployeeMapper;
import org.eustache.employemanagement.models.Department;
import org.eustache.employemanagement.models.Employee;
import org.eustache.employemanagement.models.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

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

    public String updateEmployee(Integer id, UpdateEmployeeRequest employeerequestDTO) {
        // We check if the employee exists
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found with id: " + id));
        // We update the employee details
        Optional.ofNullable(employeerequestDTO.firstName()).ifPresent(existingEmployee::setFirstname);
        Optional.ofNullable(employeerequestDTO.lastName()).ifPresent(existingEmployee::setLastname);
        Optional.ofNullable(employeerequestDTO.email()).ifPresent(existingEmployee::setEmail);
        Optional.ofNullable(employeerequestDTO.phone()).ifPresent(existingEmployee::setPhone);
        Optional.ofNullable(employeerequestDTO.dob()).ifPresent(existingEmployee::setBirthDate);
        Optional.ofNullable(employeerequestDTO.gender()).ifPresent(existingEmployee::setGender);
        Optional.ofNullable(employeerequestDTO.position()).ifPresent(existingEmployee::setPosition);
        Optional.ofNullable(employeerequestDTO.hireDate()).ifPresent(existingEmployee::setHireDate);
        Optional.ofNullable(employeerequestDTO.country()).ifPresent(existingEmployee::setCountry);
        Optional.ofNullable(employeerequestDTO.city()).ifPresent(existingEmployee::setCity);
        Optional.ofNullable(employeerequestDTO.zipcode()).ifPresent(existingEmployee::setZipcode);
        Optional.ofNullable(employeerequestDTO.street()).ifPresent(existingEmployee::setStreet);
        // We check if the job exists before we assign it to the employee
        if(employeerequestDTO.departmentId() != null) {
            Department department = departmentRepository.findById(employeerequestDTO.departmentId())
                    .orElseThrow(() -> new NotFoundException("Department not found with id: " + employeerequestDTO.departmentId()));
            existingEmployee.setDepartment(department);
        }
        // We check if the department exists before we assign it to the employee
        if(employeerequestDTO.jobId() != null) {
            Job job = jobRepository.findById(employeerequestDTO.jobId())
                    .orElseThrow(() -> new NotFoundException("Job not found with id: " + employeerequestDTO.jobId()));
            existingEmployee.setJob(job);
        }
        return "Employee updated successfully";
    }

    public String getEmployeeByRfid(String rfid) {
        Employee employee = employeeRepository.findByRfidTag(rfid)
                .orElseThrow(() -> new NotFoundException("Employee not found with RFID: " + rfid));
        return "Employee found: " + employee.getFirstname() + " " + employee.getLastname();
    }

    public EmployeeSummaryDTO getEmployeeByFirstname(String firstname) {
        Employee employee = employeeRepository.findByFirstname(firstname)
                .orElseThrow(() -> new NotFoundException("Employee not found with firstname: " + firstname));
        return employeeMapper.toEmployeeSummaryDTO(employee);
    }

    public EmployeeSummaryDTO getEmployeeByLastname(String lastname) {
        Employee employee = employeeRepository.findByLastname(lastname)
                .orElseThrow(() -> new NotFoundException("Employee not found with lastname: " + lastname));
        return employeeMapper.toEmployeeSummaryDTO(employee);
    }
}
