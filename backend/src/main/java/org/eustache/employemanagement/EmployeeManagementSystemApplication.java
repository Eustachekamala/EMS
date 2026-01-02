package org.eustache.employemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
@OpenAPIDefinition(
    info = @Info(
        title = "Employee Management System API",
        version = "1.0",
        description = "API documentation for the Employee Management System",
        license = @io.swagger.v3.oas.annotations.info.License(
            name = "Apache 2.0",
            url = "http://www.apache.org/licenses/LICENSE-2.0.html"
        ),
        contact = @io.swagger.v3.oas.annotations.info.Contact(
            name = "Eustache",
            url = "https://github.com/eustachekamala",
            email = "eustachekamala.dev@gmail.com"
        )
    )
)

@SpringBootApplication
public class EmployeeManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementSystemApplication.class, args);
    }

}
