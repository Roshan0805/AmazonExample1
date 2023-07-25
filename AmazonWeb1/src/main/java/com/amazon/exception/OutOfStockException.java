package com.amazon.exception;

/**
 * <p>
 *     Defines a general exception that occurs during servlet execution.
 * </p>
 *
 * @author Roshan
 * @version 1.0
 */
public class OutOfStockException extends Exception {

    public OutOfStockException(final String message) {
        super(message);
    }
}
