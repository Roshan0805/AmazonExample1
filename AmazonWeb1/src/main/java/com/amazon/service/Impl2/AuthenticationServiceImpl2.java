package com.amazon.service.Impl2;

import com.amazon.dao.AuthenticationDao;
import com.amazon.model.User;
import com.amazon.service.AuthenticationService;

/**
 * <p>
 * Implements the {@link AuthenticationService} to provide authentication services for {@link  User}
 * </p>
 *
 * @author Roshan
 * @version 1.0
 */
public class AuthenticationServiceImpl2 implements AuthenticationService {

    private static final AuthenticationService AUTHENTICATION_SERVICE = new AuthenticationServiceImpl2();
    private final AuthenticationDao authenticationDao;

    private AuthenticationServiceImpl2() {

        authenticationDao = AuthenticationDao.getInstance();
    }

    /**
     * <p>
     * Represents the object of {@link AuthenticationService} class can be created for only one time
     * </p>
     *
     * @return Represents object of {@link AuthenticationService}
     */
    public static AuthenticationService getInstance() {
        return AUTHENTICATION_SERVICE;
    }

    /**
     * <p>
     *  performs authentication by comparing the provided username and password with the corresponding user record in the user database.
     * </p>
     *
     * @param email    Describe User's email
     * @param password Describe User's password
     * @return True if provided username and password is correct otherwise return false
     */
    public boolean userValidation(final String email, final String password) {
        return authenticationDao.userValidation(email, password);
    }

    /**
     * <p>
     * It validates the provided user details and creates a new user account if the information is valid and not already taken.
     * </p>
     *
     * @param user Describes {@link User}
     */
    public boolean createUser(final User user) {
        return authenticationDao.createUser(user);
    }

    /**
     * <p>
     * Check whether the  user email is already exist in user list
     * </p>
     *
     * @param email User's email
     * @return True if the email id is already present on the user list
     */
    public boolean isUserEmailExists(final String email) {
        return authenticationDao.isUserEmailExists(email);
    }

    /**
     * <p>
     * Check whether the  user email is already exist in user list
     * </p>
     *
     * @param phoneNumber User's email
     * @return True if the email id is already present on the user list
     */
    public boolean isNumberExists(final String phoneNumber) {
        return authenticationDao.isNumberExists(phoneNumber);
    }
}
