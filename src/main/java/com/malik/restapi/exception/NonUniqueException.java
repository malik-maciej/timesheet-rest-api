package com.malik.restapi.exception;

public class NonUniqueException extends RuntimeException {

    public NonUniqueException() {
        super();
    }

    public NonUniqueException(final String message) {
        super(message);
    }
}