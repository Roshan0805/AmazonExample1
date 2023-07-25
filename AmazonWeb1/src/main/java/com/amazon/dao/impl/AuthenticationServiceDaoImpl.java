package com.amazon.dao.impl;

import com.amazon.dao.AuthenticationServiceDao;
import com.amazon.exception.DBException;
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
public class AuthenticationServiceDaoImpl implements AuthenticationServiceDao {

    private static final AuthenticationServiceDao AUTHENTICATION_SERVICE = new AuthenticationServiceDaoImpl();
    private final DBConnection dbConnection ;
    private final DataSourceDBConnection dataSourceDBConnection;

    private AuthenticationServiceDaoImpl() {
        dbConnection = DBConnection.getInstance();
        dataSourceDBConnection = DataSourceDBConnection.getInstance();
    }

    /**
     * Represents the object of {@link AuthenticationServiceDaoImpl} can be created only once
     *
     * @return Represents {@link AuthenticationServiceDao}
     */
    public static AuthenticationServiceDao getInstance() {
        return AUTHENTICATION_SERVICE;
    }

    /**
     * <p>
     * Provides {@link User} sign up
     * </p>
     *
     * @param user User object is passed as a parameter
     *             @throws DBException Represents any error occur while executing a query
     */
    public boolean signUp(final User user) {
        try (final Connection connection = dataSourceDBConnection.getDataSource().getConnection()) {
            final String query = "INSERT INTO USERS(NAME, EMAIL, PASSWORD, ADDRESS, PHONE_NUMBER) values (?,?,?,?,?)";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getAddress());
            statement.setString(5, user.getPhoneNumber());
            statement.execute();
            dbConnection.release(connection);

            return true;
        } catch (SQLException  exception) {
            throw new DBException(exception.getMessage());
        }
    }

    /**
     * <p>
     * Provides {@link User} sign in
     * </p>
     *
     * @param email    User's email id
     * @param password User's password
     * @return True if email and password match the user from the users list
     * @throws DBException Represents any error occur while executing a query
     */
    public boolean signIn(String email, String password) {
        try (final Connection connection = dataSourceDBConnection.getDataSource().getConnection()) {
            final String query = "SELECT * FROM USERS WHERE EMAIL = ? AND PASSWORD =?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, email);
            statement.setString(2, password);
            final ResultSet resultSet = statement.executeQuery();

            dbConnection.release(connection);

            return resultSet.next();
        } catch (SQLException exception) {
            throw new DBException(exception.getMessage());
        }
    }

    /**
     * <p>
     * Check whether the  user email is already exist in user list
     * </p>
     *
     * @param email User's email
     * @return True if the email id is already present on the user list
     * @throws DBException Represents any error occur while executing a query
     */
    public boolean isUserEmailExists(String email) {
        try (final Connection connection = dataSourceDBConnection.getDataSource().getConnection()) {
            final String query = "SELECT * FROM USERS WHERE EMAIL = ?";

            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, email);
            final ResultSet result = statement.executeQuery();

            dbConnection.release(connection);

            return result.next();
        } catch (SQLException exception) {
            throw new DBException(exception.getMessage());
        }
    }

    /**
     * <p>
     * Check whether the  user email is already exist in user list
     * </p>
     *
     * @param phoneNumber User's email
     * @return True if the email id is already present on the user list
     * @throws DBException Represents any error occur while executing a query
     */
    public boolean isNumberExists(String phoneNumber) {
        try (final Connection connection = dataSourceDBConnection.getDataSource().getConnection()) {
            final String query = "SELECT * FROM USERS WHERE PHONE_NUMBER = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, phoneNumber);
            final ResultSet result = statement.executeQuery();

            dbConnection.release(connection);

            return result.next();
        } catch (SQLException exception) {
            throw new DBException(exception.getMessage());
        }
    }
}
