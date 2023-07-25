package com.amazon.exception;

public class DBException extends RuntimeException {

    public DBException(final String message) {
        super(message);
    }
}
