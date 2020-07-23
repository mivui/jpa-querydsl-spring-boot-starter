package com.github.uinio.jpa.exception;

/**
 * Primary key not found exception
 */
public class JpaServiceException extends RuntimeException {

    public JpaServiceException(String message) {
        super(message);
    }
}
