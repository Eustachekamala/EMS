package org.eustache.employemanagement.Services;

import org.eustache.employemanagement.DAOs.DepartmentRepository;
import org.eustache.employemanagement.DTOs.DepartmentDTO;
import org.eustache.employemanagement.Mappers.DepartmentMapper;
import org.eustache.employemanagement.models.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentMapper departmentMapper;

    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(departmentMapper::toDTO)
                .collect(Collectors.toList());
    }
}
