package org.eustache.employemanagement.DTOs.Requests;

import org.eustache.employemanagement.models.RoleType;

public record UserRegisterDTO(
        String username,
        String email,
        String password,
        RoleType role // enum
) {
}
