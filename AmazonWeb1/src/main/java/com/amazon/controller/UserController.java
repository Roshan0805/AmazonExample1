package com.amazon.controller;

import com.amazon.model.User;
import com.amazon.service.Impl2.UserServiceImpl2;
import com.amazon.service.UserService;

import java.util.Collection;

/**
 * <p>
 * Provides Control between service and the view for {@link User} related details
 * </p>
 *
 * @author Roshan
 * @version 1.0
 */
public class UserController {

    private static final UserController USER_CONTROLLER = new UserController();
    private final UserService userService;

    private UserController() {
        userService = UserServiceImpl2.getInstance();
    }

    /**
     * <p>
     * method to provide access to the single instance
     * </p>
     *
     * @return Represents {@link UserController}
     */
    public static UserController getInstance() {
        return USER_CONTROLLER;
    }

    /**
     * <p>
     * Retrieves the user details using user's id
     * </p>
     *
     * @param id Represents user's id
     * @return {@link User} from the amazon service
     */
    public User getDetail(final Long id) {
        return userService.get(id);
    }

    /**
     * <p>
     * Deletes the user from the user list
     * </p>
     *
     * @param userId Represents {@link User}
     * @return True if the user is deleted successfully otherwise return false
     */
    public boolean delete(final Long userId) {
        return userService.delete(userId);
    }

    /**
     * <p>
     *     Retrieves a list of all users from the data source
     * </p>
     *
     * @return A list containing all user records retrieved from the data source.
     */
    public Collection<User> getUsers() {
        return userService.getUsers();
    }

    /**
     * <p>
     * This method is used to modify existing user details in database
     * </p>
     *
     * @param user   Represents the updated {@link User}
     * @param userId Represents the user's id
     * @return True if updated successfully
     */
    public boolean update(final User user, final Long userId) {
        return userService.update(user, userId);
    }
}

