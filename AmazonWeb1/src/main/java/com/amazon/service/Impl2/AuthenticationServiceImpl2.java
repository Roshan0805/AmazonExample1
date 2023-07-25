package com.amazon.service.Impl2;

import com.amazon.dao.AuthenticationServiceDao;
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
    private final AuthenticationServiceDao authenticationServiceDao;

    private AuthenticationServiceImpl2() {

        authenticationServiceDao = AuthenticationServiceDao.getInstance();
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
     * Provides {@link User} sign in
     * </p>
     *
     * @param email    User's email id
     * @param password User's password
     * @return True if email and password match the user from the users list
     */
    public boolean signIn(String email, String password) {
        return authenticationServiceDao.signIn(email, password);
    }

    /**
     * <p>
     * Provides {@link User} sign up
     * </p>
     *
     * @param user User object is passed as a parameter
     */
    public boolean signUp(User user) {
        return authenticationServiceDao.signUp(user);
    }

    /**
     * <p>
     * Check whether the  user email is already exist in user list
     * </p>
     *
     * @param email User's email
     * @return True if the email id is already present on the user list
     */
    public boolean isUserEmailExists(String email) {
        return authenticationServiceDao.isUserEmailExists(email);
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
        return authenticationServiceDao.isNumberExists(phoneNumber);
    }

}
