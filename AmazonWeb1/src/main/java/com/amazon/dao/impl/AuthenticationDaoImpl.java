package com.amazon.dao.impl;

import com.amazon.dao.AuthenticationDao;
import com.amazon.exception.DatabaseException;
import com.amazon.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * Represents the authentication service for {@link User}
 * </p>
 *
 * @author Roshan B
 * @version 1.0
 */
public class AuthenticationDaoImpl implements AuthenticationDao {

    private static final AuthenticationDao AUTHENTICATION_SERVICE = new AuthenticationDaoImpl();
    private final DatabaseConnector database;

    private AuthenticationDaoImpl() {
        database = DatabaseConnector.getInstance();
    }

    /**
     * <p>
     *     Method to provide access to the single instance
     * </p>
     *
     * @return Represents {@link AuthenticationDao}
     */
    public static AuthenticationDao getInstance() {
        return AUTHENTICATION_SERVICE;
    }

    /**
     * <p>
     * It validates the provided user details and creates a new user account if the information is valid and not already taken.
     * </p>
     *
     * @param user Describes {@link User}
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public boolean createUser(final User user) {
        try (final Connection connection = database.getDataSource().getConnection()) {
            final String query = "INSERT INTO USERS(NAME, EMAIL, PASSWORD, ADDRESS, PHONE_NUMBER) values (?,?,?,?,?)";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getAddress());
            statement.setString(5, user.getPhoneNumber());
            statement.execute();

            return true;
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    /**
     * <p>
     * performs authentication by comparing the provided username and password with the corresponding user record in the user database.
     * </p>
     *
     * @param email    Describe User's email
     * @param password Describe User's password
     * @return True if provided username and password is correct otherwise return false
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public boolean userValidation(final String email, final String password) {
        try (final Connection connection = database.getDataSource().getConnection()) {
            final String query = "SELECT ID FROM USERS WHERE EMAIL = ? AND PASSWORD =?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, email);
            statement.setString(2, password);
            final ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    /**
     * <p>
     * Check whether the  user email is already exist in user list
     * </p>
     *
     * @param email User's email
     * @return True if the email id is already present on the user list
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public boolean isUserEmailExists(final String email) {
        try (final Connection connection = database.getDataSource().getConnection()) {
            final String query = "SELECT ID FROM USERS WHERE EMAIL = ?";

            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, email);
            final ResultSet result = statement.executeQuery();

            return result.next();
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    /**
     * <p>
     * Check whether the  user email is already exist in user list
     * </p>
     *
     * @param phoneNumber User's email
     * @return True if the email id is already present on the user list
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public boolean isNumberExists(final String phoneNumber) {
        try (final Connection connection = database.getDataSource().getConnection()) {
            final String query = "SELECT ID FROM USERS WHERE PHONE_NUMBER = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, phoneNumber);
            final ResultSet result = statement.executeQuery();

            return result.next();
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }
}
