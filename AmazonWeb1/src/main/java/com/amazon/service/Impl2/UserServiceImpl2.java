package com.amazon.service.Impl2;

import com.amazon.dao.UserDao;
import com.amazon.dao.impl.UserDaoImpl;
import com.amazon.model.User;
import com.amazon.service.UserService;

import java.util.Collection;

/**
 * <p>
 * Implements the {@link UserDaoImpl} to provide services for {@link  User}
 * </p>
 *
 * @author Roshan
 * @version 1.0
 */
public class UserServiceImpl2 implements UserService {

    private static final UserService USER_SERVICE = new UserServiceImpl2();
    private final UserDao userDao;

    private UserServiceImpl2() {
        userDao = UserDaoImpl.getInstance();
    }

    public static UserService getInstance() {
        return USER_SERVICE;
    }

    /**
     * <p>
     * Gets the user details from the user list
     * </p>
     *
     * @param id User email
     * @return User object from the user list
     */
    public User get(final Long id) {
        return userDao.getDetails(id);
    }

    /**
     * <p>
     * Delete the user from the user list
     * </p>
     *
     * @param userId Represents the id of {@link User}
     * @return Boolean true is the user is deleted successfully
     */
    public boolean delete(final Long userId) {
        return userDao.deleteUser(userId);
    }

    /**
     * <p>
     *     Retrieves a list of all users from the data source
     * </p>
     *
     * @return A list containing all user records retrieved from the data source.
     */
    public Collection<User> getUsers() {
        return userDao.getUsers();
    }

    /**
     * Represents the update of {@link User}
     *
     * @param user Represents {@link User}
     * @param id   Represents the id of user
     * @return True if the user is updated successfully
     */
    public boolean update(final User user, final Long id) {
        return userDao.update(user, id);
    }
}
