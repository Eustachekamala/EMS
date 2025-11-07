package org.eustache.employemanagement.DTOs.Responses;


public record JobResponseDTO(
        Integer id,
        String departmentName,
        String title,
        String description
) {

}
