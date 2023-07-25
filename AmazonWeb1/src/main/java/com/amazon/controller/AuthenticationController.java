package com.amazon.controller;

import com.amazon.model.User;
import com.amazon.service.AuthenticationService;
import com.amazon.service.Impl2.AuthenticationServiceImpl2;
import com.amazon.service.impl.AuthenticationServiceImpl;

/**
 * <p>
 *     Represents the authentication controller
 * </p>
 * @author Roshan
 * @version 1.0
 */
public class AuthenticationController {

    private static final AuthenticationController AUTHENTICATION_CONTROLLER = new AuthenticationController();
    private final AuthenticationService authenticationService2;
    private final AuthenticationService authenticationService;

    private AuthenticationController () {
        authenticationService2 = AuthenticationServiceImpl2.getInstance();
        authenticationService = AuthenticationServiceImpl.getInstance();
    }

    /**
     * <p>
     *     Represents the instance of {@link AuthenticationController} created only ones
     * </p>
     * @return Represents {@link AuthenticationController}
     */
    public static AuthenticationController getInstance() {
        return AUTHENTICATION_CONTROLLER;
    }

    /**
     * <p>
     * Provides {@link User} sign up
     * </p>
     *
     * @param user Represents {@link User}
     */
    public boolean signUp(User user) {
        return authenticationService2.signUp(user);
    }

    /**
     * <p>
     * Provides user sign in for user
     * </p>
     *
     * @param email    User's email
     * @param password User's password
     * @return Boolean value from the validateUser method on service
     */
    public boolean signIn(final String email, final String password) {
        return authenticationService2.signIn(email, password);
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
        return authenticationService2.isUserEmailExists(userEmail);
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
        return authenticationService2.isNumberExists(phoneNumber);
    }
}
