package org.eustache.employemanagement.Mappers;

// import org.eustache.employemanagement.DAOs.AddressRepository;
// import org.eustache.employemanagement.DAOs.DepartmentRepository;
import org.eustache.employemanagement.DAOs.JobRepository;
import org.eustache.employemanagement.DTOs.Requests.EmployeeRequestDTO;
import org.eustache.employemanagement.DTOs.Responses.EmployeeSummaryDTO;
import org.eustache.employemanagement.Exceptions.NotFoundException;
// import org.eustache.employemanagement.models.Address;
// import org.eustache.employemanagement.models.Department;
import org.eustache.employemanagement.models.Employee;
import org.eustache.employemanagement.models.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    // @Autowired  
    // private DepartmentRepository departmentRepository;
    @Autowired
    private JobRepository jobRepository;
    // @Autowired
    // private AddressRepository addressRepository;

    public Employee toEntity(EmployeeRequestDTO employeeRequestDTO) {
        if (employeeRequestDTO == null) return null;

        // Here we check if the department exists
        // Department department = departmentRepository.findById(employeeRequestDTO.departmentId())
        //         .orElseThrow(() -> new NotFoundException("Department not found with id: " + employeeRequestDTO.departmentId()));

        // Here we check if the job exists
        Job job = jobRepository.findById(employeeRequestDTO.jobId())
                .orElseThrow(() -> new NotFoundException("Job not found with id: " + employeeRequestDTO.jobId()));

        // Here we check if the address exists
        // Address address = addressRepository.findById(employeeRequestDTO.addressId())
        //         .orElseThrow(() -> new NotFoundException("Address not found with id: " + employeeRequestDTO.addressId()));

        Employee employee = new Employee();
        employee.setFirstname(employeeRequestDTO.firstName());
        employee.setLastname(employeeRequestDTO.lastName());
        employee.setEmail(employeeRequestDTO.email());
        employee.setPhone(employeeRequestDTO.phone());
        employee.setBirthDate(employeeRequestDTO.dob());
        employee.setGender(employeeRequestDTO.gender());
        // employee.setDepartment(department);
        employee.setJob(job);
        // employee.setAddress(address);
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
