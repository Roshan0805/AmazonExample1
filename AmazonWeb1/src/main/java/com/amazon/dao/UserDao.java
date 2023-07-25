package com.amazon.dao;

import com.amazon.model.User;

import java.util.Collection;

/**
 * <p>
 * Represents the {@link User} service
 * </p>
 *
 * @author Roshan
 * @version 1.0
 */
public interface UserDao {


    /**
     * <p>
     * Gets the user details from the user list
     * </p>
     *
     * @param id User email
     * @return User object from the user list
     */
    User getDetails(final Long id);

    /**
     * <p>
     * Delete the user from the user list
     * </p>
     *
     * @param user_id Represents the id of {@link User}
     * @return Boolean true is the user is deleted successfully
     */
    boolean deleteUser(final Long user_id);

    /**
     * <p>
     *     Retrieves a list of all users from the data source
     * </p>
     *
     * @return A list containing all user records retrieved from the data source.
     */
    Collection<User> getUsers();

    /**
     * <p>
     *     Represents the {@link User}update
     * </p>
     * @param user Represents {@link User}
     * @param userId Represents the id of user
     * @return True if the user object is updated successfully
     */
    boolean update(final User user, final Long userId);
}
