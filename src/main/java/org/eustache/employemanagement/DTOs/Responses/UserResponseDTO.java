package org.eustache.employemanagement.DTOs.Responses;

import org.eustache.employemanagement.models.RoleType;

public record UserResponseDTO(
        Integer id,
        String username,
        String email,
        RoleType role
) {
}
