package org.eustache.employemanagement.Mappers;

import org.eustache.employemanagement.DTOs.DepartmentDTO;
import org.eustache.employemanagement.models.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {
    public DepartmentDTO toDTO(Department department) {
        if (department == null)
            return null;
        return new DepartmentDTO(
                department.getDepartmentId(),
                department.getName().name());
    }
}
