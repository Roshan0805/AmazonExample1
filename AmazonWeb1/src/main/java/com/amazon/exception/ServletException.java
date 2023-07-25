package com.amazon.exception;

/**
 * <p>
 *     Defines a general exception that occurs during servlet execution.
 * </p>
 * @author Roshan B
 * @version 1.0
 */
public class ServletException extends RuntimeException {

    public ServletException(final String message) {
        super (message);
    }
}
