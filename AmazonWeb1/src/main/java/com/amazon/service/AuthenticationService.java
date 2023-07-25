package com.amazon.service;

import com.amazon.model.User;

/**
 * <p>
 *     Provide Service for Authentication
 * </p>
 *
 * @author Roshan B
 * @version 1.0
 */
public interface AuthenticationService {

    /**
     * <p>
     * Provides {@link User} sign in
     * </p>
     *
     * @param email    User's email id
     * @param password User's password
     * @return True if email and password match the user from the users list
     */
    boolean signIn(final String email, final String password);

    /**
     * <p>
     * Provides {@link User} sign up
     * </p>
     *
     * @param user User object is passed as a parameter
     */
    boolean signUp(final User user);

    /**
     * <p>
     * Check whether the  user email is already exist in user list
     * </p>
     *
     * @param email User's email
     * @return True if the email id is already present on the user list
     */
    boolean isUserEmailExists(final String email);

    /**
     * <p>
     * Check whether the  user email is already exist in user list
     * </p>
     *
     * @param phoneNumber User's email
     * @return True if the email id is already present on the user list
     */
    boolean isNumberExists(final String phoneNumber);
}
