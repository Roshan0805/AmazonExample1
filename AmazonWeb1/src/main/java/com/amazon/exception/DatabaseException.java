package com.amazon.exception;

/**
 * <p>
 *     Defines a general exception that occurs during database execution.
 * </p>
 * @author Roshan B
 * @version 1.0
 */
public class DatabaseException extends RuntimeException {

    public DatabaseException(final String message) {
        super(message);
    }
}
