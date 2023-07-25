package com.amazon.service.impl;

import com.amazon.model.User;
import com.amazon.service.AuthenticationService;

/**
 * <p>
 *     Describes the authentication service for the user
 * </p>
 * @author Roshan B
 * @version 1.0
 *
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final AuthenticationServiceImpl AUTHENTICATION_SERVICE = new AuthenticationServiceImpl();
    private Long userId;

    private AuthenticationServiceImpl() {
        this.userId = 1L;
    }

    /**
     * <p>
     *     Method to provide access to the single instance for accessing
     * </p>
     * @return {@link AuthenticationService}
     */
    public static AuthenticationService getInstance() {
        return AUTHENTICATION_SERVICE;
    }

    /**
     * <p>
     * It validates the provided user details and creates a new user account if the information is valid and not already taken.
     * </p>
     *
     * @param user Describes {@link User}
     */
    public boolean createUser(User user) {
        try {
            final Long id = generateId();

            user.setId(id);
            UserServiceImpl.USERS.put(id, user);

            return true;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return false;
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
        for (final User existingUser :  UserServiceImpl.USERS.values()) {

            if ((existingUser.getEmail().equals(email)) && (existingUser.getPassword().equals(password))) {
                return true;
            }
        }

        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @param email {@link User} email
     * @return True If the email is already exists
     */
    public boolean isUserEmailExists(String email) {
        for (final User user :  UserServiceImpl.USERS.values()) {

            if (user.getEmail().equals(email)) {
                return true;
            }
        }

        return false;
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
        for (final User user :  UserServiceImpl.USERS.values()) {

            if (user.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }

        return false;
    }

    /**
     * <p>
     *     Represent generating Id for {@link User}
     * </p>
     * @return Represents userId
     */
    public Long generateId() {
        return userId++;
    }
}
