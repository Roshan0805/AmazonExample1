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

    public static final Map<Long, User> usersList = new HashMap<>();

    private UserServiceImpl() {
    }

    /**
     * <p>
     * Represents the object of {@link UserServiceImpl} class can be created for only one time
     * </p>
     *
     * @return Represents {@link UserServiceImpl}
     */
    public static UserService getInstance() {
        return USER_SERVICE;
    }


    /**
     * {@inheritDoc}
     *
     * @param id Represents user id
     * @return Represents {@link User}
     */
    public User get(final Long id) {
        return usersList.get(id);
    }

    /**
     * {@inheritDoc}
     *
     * @param user_id User object wants to delete
     * @return True is the user is deleted successfully
     */
    public boolean delete(final Long user_id) {
        if (0 == user_id) {
            return false;
        }
        usersList.remove(user_id);

        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @return Represents Collection of {@link User} in the usersList
     */
    public Collection<User> getAllUser() {
        return usersList.values();
    }

    /**
     * Represents the update of {@link User}
     *
     * @param user Represents {@link User}
     * @param id   Represents the id of user
     * @return True if the user is updated successfully
     */
    public boolean update(User user, Long id) {
        try {
            usersList.put(id, user);

            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}