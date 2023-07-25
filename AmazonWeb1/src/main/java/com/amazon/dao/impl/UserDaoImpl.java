package com.amazon.dao.impl;

import com.amazon.dao.UserDao;
import com.amazon.exception.DatabaseException;
import com.amazon.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Implements the {@link UserDao} and provide {@link User} service
 * </p>
 *
 * @author Roshan
 * @version 1.0
 */
public class UserDaoImpl implements UserDao {

    private static final UserDao USER_SERVICE_DAO = new UserDaoImpl();
    private final DatabaseConnector database;

    private UserDaoImpl() {
        this.database = DatabaseConnector.getInstance();
    }

    /**
     * <p>
     * Method to provide access to the single instance for accessing
     * </p>
     *
     * @return Represents {@link UserDao}
     */
    public static UserDao getInstance() {
        return USER_SERVICE_DAO;
    }


    /**
     * <p>
     * Gets the user details from the user list
     * </p>
     *
     * @param id User email
     * @return User object from the user list
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public User getDetails(final Long id) {
        try (final Connection connection = database.getDataSource().getConnection()) {
            final String query = "SELECT * FROM USERS WHERE ID = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, id);
            final ResultSet result = statement.executeQuery();

            if (result.next()) {
                final User user = new User();

                user.setId(result.getLong(1));
                user.setName(result.getString(2));
                user.setEmail(result.getString(3));
                user.setPassword(result.getString(4));
                user.setAddress(result.getString(5));
                user.setPhoneNumber(result.getString(6));

                return user;
            }
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
        return null;
    }

    /**
     * <p>
     * Delete the user from the user list
     * </p>
     *
     * @param user_id Represents the id of {@link User}
     * @return Boolean true is the user is deleted successfully
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public boolean deleteUser(final Long user_id) {
        try (final Connection connection = database.getDataSource().getConnection()) {
            final String query = "DELETE FROM USERS WHERE ID = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, user_id);
            statement.execute();

            return true;
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    /**
     * <p>
     *     Retrieves a list of all users from the data source
     * </p>
     *
     * @return A list containing all user records retrieved from the data source.
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public List<User> getUsers() {
        try (final Connection connection = database.getDataSource().getConnection()) {
            final String query = "SELECT * FROM USERS";
            final PreparedStatement statement = connection.prepareStatement(query);
            final ResultSet result = statement.executeQuery();
            final List<User> userList = new ArrayList<>();

            if (result.next()) {
                final User user = new User();

                user.setId(result.getLong(1));
                user.setName(result.getString(2));
                user.setEmail(result.getString(3));
                user.setPassword(result.getString(4));
                user.setAddress(result.getString(5));
                user.setPhoneNumber(result.getString(6));
                userList.add(user);
            }

            return userList;
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    /**
     * <p>
     * Represents the user update
     * </p>
     *
     * @param user   Represents the updated {@link User}
     * @param userId Represents the user's id
     * @return true if updated successfully
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public boolean update(final User user, final Long userId) {
        try (final Connection connection = database.getDataSource().getConnection()) {
            final String query = "UPDATE USERS SET NAME = ?, EMAIL = ?, PASSWORD = ?, ADDRESS = ?, PHONE_NUMBER = ?  where ID = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getAddress());
            statement.setString(5, user.getPhoneNumber());
            statement.setLong(6, userId);
            statement.execute();

            return true;
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }
}
