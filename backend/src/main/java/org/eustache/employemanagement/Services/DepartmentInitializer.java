package org.eustache.employemanagement.Services;

import java.util.Arrays;

import org.eustache.employemanagement.DAOs.DepartmentRepository;
import org.eustache.employemanagement.models.Department;
import org.eustache.employemanagement.models.DepartmentType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DepartmentInitializer implements CommandLineRunner{
    private final DepartmentRepository departmentRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize departments if they do not exist
        Arrays.stream(DepartmentType.values())
            .forEach(deptType -> {
                departmentRepository.findByName(deptType)
                    .orElseGet(() -> {
                        Department dept = new Department();
                        dept.setName(deptType);
                        return departmentRepository.save(dept);
                    });
            });
    }
}
