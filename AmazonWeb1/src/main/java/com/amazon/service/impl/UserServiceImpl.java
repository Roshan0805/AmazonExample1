package com.amazon.service.impl;

import com.amazon.service.UserService;
import com.amazon.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Implements the {@link UserService} to provide services for {@link  User}
 * </p>
 *
 * @author Roshan
 * @version 1.0
 */
public class UserServiceImpl implements UserService {

    private static final UserServiceImpl USER_SERVICE = new UserServiceImpl();

    public static final Map<Long, User> USERS = new HashMap<>();

    private UserServiceImpl() {
    }

    /**
     * <p>
     * Method to provide access to the single instance for accessing
     * </p>
     *
     * @return Represents {@link UserServiceImpl}
     */
    public static UserService getInstance() {
        return USER_SERVICE;
    }

    /**
     * <p>
     * Retrieves the user details
     * </p>
     *
     * @param id Represents user id
     * @return Represents {@link User}
     */
    public User get(final Long id) {
        return USERS.get(id);
    }

    /**
     * <p>
     * Describes the user delete from database
     * </p>
     *
     * @param user_id User object wants to delete
     * @return True is the user is deleted successfully
     */
    public boolean delete(final Long user_id) {
        if (0 == user_id) {
            return false;
        }
        USERS.remove(user_id);

        return true;
    }

    /**
     * <p>
     * Retrieves a list of all users from the data source
     * </p>
     *
     * @return A list containing all user records retrieved from the data source.
     */
    public Collection<User> getUsers() {
        return USERS.values();
    }

    /**
     * Represents the update of {@link User}
     *
     * @param user Represents {@link User}
     * @param id   Represents the id of user
     * @return True if the user is updated successfully
     */
    public boolean update(final User user, final Long id) {
        try {
            USERS.put(id, user);

            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * <p>
     *     Retrieves the user's list
     * </p>
     * @return Represents the list of users
     */
    public Map<Long, User> getUsersList() {
        return USERS;
    }
}