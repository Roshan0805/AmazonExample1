package com.amazon.service;

import com.amazon.model.User;
import com.amazon.service.impl.UserServiceImpl;

import java.util.Collection;

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
     * Represents the {@link UserService} interface implemented class object can be created for only one time
     * </p>
     *
     * @return Represents the object of {@link UserServiceImpl}
     */
    static UserService getInstance() {
        return UserServiceImpl.getInstance();
    }


    /**
     * <p>
     * Gets the user details from the user list
     * </p>
     *
     * @param id User email
     * @return {@link User} object from the user list
     */
    User get(final Long id);

    /**
     * <p>
     * Delete the user from the user list
     * </p>
     *
     * @param user_id Represents the id of {@link User}
     * @return Boolean true is the user is deleted successfully
     */
    boolean delete(final Long user_id);

    /**
     * Represents all the {@link User} details in the usersList
     *
     * @return Represents collection of {@link User}
     */
    Collection<User> getAllUser();

    /**
     * Represents the update of {@link User}
     *
     * @param user Represents {@link User}
     * @param id   Represents the id of user
     * @return True if the user is updated successfully
     */
    boolean update(final User user, final Long id);
}