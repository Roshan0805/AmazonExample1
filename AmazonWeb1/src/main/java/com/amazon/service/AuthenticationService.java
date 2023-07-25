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
     *  performs authentication by comparing the provided username and password with the corresponding user record in the user database.
     * </p>
     *
     * @param email    Describe User's email
     * @param password Describe User's password
     * @return True if provided username and password is correct otherwise return false
     */
    boolean userValidation(final String email, final String password);

    /**
     * <p>
     * It validates the provided user details and creates a new user account if the information is valid and not already taken.
     * </p>
     *
     * @param user Describes {@link User}
     */
    boolean createUser(final User user);

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
