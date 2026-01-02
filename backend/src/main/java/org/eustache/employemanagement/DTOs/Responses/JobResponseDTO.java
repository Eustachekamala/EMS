package org.eustache.employemanagement.DTOs.Responses;

public record JobResponseDTO(
        Integer jobId,
        String departmentName,
        Integer departmentId,
        String title,
        String description,
        java.math.BigDecimal salary) {

}
