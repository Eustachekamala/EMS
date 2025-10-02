package org.eustache.employemanagement.DTOs.Requests;

public record AddressRequestDto(
        String street,
        String city,
        String country,
        String zipCode
) {
}
