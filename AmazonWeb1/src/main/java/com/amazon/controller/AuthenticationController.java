package com.amazon.controller;

import com.amazon.model.User;
import com.amazon.service.AuthenticationService;
import com.amazon.service.Impl2.AuthenticationServiceImpl2;

/**
 * <p>
 * Describes controller between authentication servlet and authentication service
 * </p>
 *
 * @author Roshan
 * @version 1.0
 */
public class AuthenticationController {

    private static final AuthenticationController AUTHENTICATION_CONTROLLER = new AuthenticationController();
    private final AuthenticationService authenticationService;

    private AuthenticationController() {
        this.authenticationService = AuthenticationServiceImpl2.getInstance();
    }

    /**
     * <p>
     * Method to provide access to the single instance
     * </p>
     *
     * @return Represents {@link AuthenticationController}
     */
    public static AuthenticationController getInstance() {
        return AUTHENTICATION_CONTROLLER;
    }

    /**
     * <p>
     * It validates the provided user details and creates a new user account if the information is valid and not already taken.
     * </p>
     *
     * @param user Describes {@link User}
     */
    public boolean signUp(User user) {
        return authenticationService.createUser(user);
    }

    /**
     * <p>
     * performs authentication by comparing the provided username and password with the corresponding user record in the user database.
     * </p>
     *
     * @param email    Describe User's email
     * @param password Describe User's password
     * @return True if provided username and password is correct otherwise return false
     */
    public boolean signIn(final String email, final String password) {
        return authenticationService.userValidation(email, password);
    }


    /**
     * <p>
     * Check whether the  user email is already exist in user list
     * </p>
     *
     * @param userEmail User's email
     * @return True if the email id is already present on the user list
     */
    public boolean isUserEmailExists(String userEmail) {
        return authenticationService.isUserEmailExists(userEmail);
    }

    /**
     * <p>
     * Check whether the  user email is already exist in user list
     * </p>
     *
     * @param phoneNumber User's email
     * @return True if the email id is already present on the user list
     */
    public boolean isNumberExists(String phoneNumber) {
        return authenticationService.isNumberExists(phoneNumber);
    }
}
