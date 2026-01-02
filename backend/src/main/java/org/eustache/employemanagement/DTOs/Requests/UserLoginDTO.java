package org.eustache.employemanagement.DTOs.Requests;

public record UserLoginDTO(
        String username,
        String password
) {
}
