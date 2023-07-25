package com.amazon.dao;

import com.amazon.dao.impl.AuthenticationServiceDaoImpl;
import com.amazon.model.User;

/**
 * <p>
 * Represents the {@link AuthenticationServiceDao} service
 * </p>
 *
 * @author Roshan
 * @version 1.0
 */
public interface AuthenticationServiceDao {

    /**
     * <p>
     *     Represents the object for {@link AuthenticationServiceDao} can be created only once
     * </p>
     * @return Represents  {@link AuthenticationServiceDao}
     */
    static AuthenticationServiceDao getInstance() {
        return AuthenticationServiceDaoImpl.getInstance();
    }

    /**
     * <p>
     * Provides {@link User} sign up
     * </p>
     *
     * @param user Represents {@link User}
     * @return True if email and password match the user from the users list
     */
    boolean signUp(final User user);

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
