package org.eustache.employemanagement.DTOs;

import org.eustache.employemanagement.models.RoleType;

public record RoleDTO(
        Integer id,
        RoleType name
) {
}
