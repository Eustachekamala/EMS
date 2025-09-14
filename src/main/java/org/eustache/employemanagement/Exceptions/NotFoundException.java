package org.eustache.employemanagement.Exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
    super(message);
    }
}
