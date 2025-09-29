FROM openjdk:21-jdk-slim

LABEL authors="eustache"

WORKDIR /employeeManagementApp

COPY target/EmployeeManagementSystem-0.0.1-SNAPSHOT.jar employeeManagementApp.jar

ENTRYPOINT ["java", "-jar", "employeeManagementApp.jar"]