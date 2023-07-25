package com.amazon.dao.impl;

import com.amazon.dao.UserServiceDao;
import com.amazon.exception.DBException;
import com.amazon.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

/**
 * <p>
 * Implements the {@link UserServiceDao} and provide {@link User} service
 * </p>
 *
 * @author Roshan
 * @version 1.0
 */
public class UserServiceDaoImpl implements UserServiceDao {

    private static final UserServiceDao USER_SERVICE_DAO = new UserServiceDaoImpl();
    private final DBConnection dbConnection ;

    private UserServiceDaoImpl() {
       dbConnection = DBConnection.getInstance();
    }

    /**
     * <p>
     * Represents the object of {@link UserServiceDaoImpl} class can be created for only one time
     * </p>
     *
     * @return Represents {@link UserServiceDao}
     */
    public static UserServiceDao getInstance() {
        return USER_SERVICE_DAO;
    }


    /**
     * <p>
     * Gets the user details from the user list
     * </p>
     *
     * @param id User email
     * @return User object from the user list
     * @throws DBException Represents any error occur while executing a query
     */
    public User getDetails(final Long id) {
        try (final Connection connection = dbConnection.get()) {
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
                dbConnection.release(connection);

                return user;
            }
        } catch (SQLException | InterruptedException exception) {
            throw new DBException(exception.getMessage());
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
     * @throws DBException Represents any error occur while executing a query
     */
    public boolean deleteUser(final Long user_id) {
        try (final Connection connection = dbConnection.get()) {
            final String query = "DELETE FROM USERS WHERE ID = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, user_id);
            statement.execute();
            dbConnection.release(connection);

            return true;
        } catch (SQLException | InterruptedException exception) {
            throw new DBException(exception.getMessage());
        }
    }

    /**
     * Represents all the {@link User} details in the usersList
     *
     * @return Represents collection of {@link User}
     * @throws DBException Represents any error occur while executing a query
     */
    public Collection<User> getAllUser() {
        try (final Connection connection = dbConnection.get()) {
            final String query = "SELECT * FROM USERS";
            final PreparedStatement statement = connection.prepareStatement(query);
            final ResultSet result = statement.executeQuery();
            final Collection<User> userList = new LinkedList<>();

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
            dbConnection.release(connection);

            return userList;
        } catch (SQLException | InterruptedException exception) {
            throw new DBException(exception.getMessage());
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
     * @throws DBException Represents any error occur while executing a query
     */
    public boolean update(final User user, final Long userId) {
        try (final Connection connection = dbConnection.get()) {
            final String query = "UPDATE USERS SET NAME = ?, EMAIL = ?, PASSWORD = ?, ADDRESS = ?, PHONE_NUMBER = ?  where ID = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getAddress());
            statement.setString(5, user.getPhoneNumber());
            statement.setLong(6, userId);
            statement.execute();
            dbConnection.release(connection);

            return true;
        } catch (SQLException | InterruptedException exception) {
            throw new DBException(exception.getMessage());
        }
    }
}
