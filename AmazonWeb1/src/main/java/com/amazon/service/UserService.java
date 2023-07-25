package com.amazon.service;

import com.amazon.model.User;
import com.amazon.service.impl.UserServiceImpl;

import java.util.Collection;
import java.util.Map;

/**
 * <p>
 * Provides Services for {@link User}
 * </p>
 *
 * @author Roshan
 * @version 1.0
 */
public interface UserService {

    /**
     * <p>
     * Method to provide access to the single instance for accessing
     * </p>
     *
     * @return Represents the object of {@link UserServiceImpl}
     */
    static UserService getInstance() {
        return UserServiceImpl.getInstance();
    }


    /**
     * <p>
     * Retrieves the user details from the user list
     * </p>
     *
     * @param id Represents user's id
     * @return describes {@link User} object
     */
    User get(final Long id);

    /**
     * <p>
     * Delete the user from the database
     * </p>
     *
     * @param user_id Represents the id of {@link User}
     * @return Boolean true is the user is deleted successfully
     */
    boolean delete(final Long user_id);

    /**
     * <p>
     *     Retrieves a list of all users from the database
     * </p>
     *
     * @return A list containing all user records retrieved from the data source.
     */
    Collection<User> getUsers();

    /**
     * Describes the update of {@link User}
     *
     * @param user Represents {@link User}
     * @param id   Represents the id of user
     * @return True if the user is updated successfully otherwise return false
     */
    boolean update(final User user, final Long id);

}