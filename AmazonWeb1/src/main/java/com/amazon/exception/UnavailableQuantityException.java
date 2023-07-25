package com.amazon.exception;

/**
 * <p>
 *     Represent the Unavailable quantity exception
 * </p>
 *
 * @author Roshan
 * @version 1.0
 */
public class UnavailableQuantityException extends Exception {

    public UnavailableQuantityException(final String message) {
        super(message);
    }
}
