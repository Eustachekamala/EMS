package org.eustache.employemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;;
@OpenAPIDefinition(
    info = @Info(
        title = "Employee Management System API",
        version = "1.0",
        description = "API documentation for the Employee Management System"
    ), 
    servers = {
        @Server(url = "http://localhost:8080/api/v1", description = "Local server")
    }
)

@SpringBootApplication
public class EmployeeManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementSystemApplication.class, args);
    }

}
