package org.eustache.employemanagement.DTOs;

public record AddressDTO(
        Integer id,
        String street,
        String city,
        String country,
        String zipCode
) {
}
