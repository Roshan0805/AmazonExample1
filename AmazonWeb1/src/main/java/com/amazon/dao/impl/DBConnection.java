package com.amazon.dao.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * <p>
 * Represents the DATABASE Connection
 * </p>
 *
 * @author Roshan B
 * @version 1.0
 */

public class DBConnection {

    private static DBConnection DB_CONNECTION;
    private final Integer maxSize ;
    private static BlockingQueue<Connection> pool;


    private DBConnection() {
        maxSize = 15;
        pool = new ArrayBlockingQueue<>(maxSize);

        initializeObjects();
    }

    /**
     * <p>
     * Represents the instance of DBConnection class can be created for only once
     * </p>
     *
     * @return Represents {@link DBConnection }
     */
    public static DBConnection getInstance() {
        if (null == DB_CONNECTION) {
            DB_CONNECTION = new DBConnection();
        }
        return DB_CONNECTION;
    }

    /**
     * <p>
     * Represent the object pool pattern's object initialization,
     * </p>
     */
    private void initializeObjects() {
        try {
            for (int i = 0; i < maxSize; i++) {
                final Connection connection = getConnection();

                if (null != connection) {
                    pool.offer(connection);
                }
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * <p>
     * Represents the database connection for the given database url, name and password
     * </p>
     *
     * @return Connection object
     * @throws SQLException if any error occur in connection it throws a sql exception
     */
    private Connection getConnection() throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
        final Properties properties = new Properties();
        try (FileInputStream file = new FileInputStream("C:\\Users\\krith\\IdeaProjects\\amazon\\src\\main\\resources\\ApplicationProperties")) {
            properties.load(file);
            final String url = properties.getProperty("database_url");
            final String user = properties.getProperty("user");
            final String password = properties.getProperty("password");

            return DriverManager.getConnection(url, user, password);
        } catch (IOException exception) {
            return null;
        }
    }

    /**
     * <p>
     * Represents getting the connection object
     * </p>
     *
     * @return Connection object
     * @throws InterruptedException if any error occur during getting connection it throws the InterruptedException
     */

    public Connection get() throws InterruptedException {
        return pool.take();
    }

    /**
     * <p>
     * Represents the connection object to release from the source
     * </p>
     *
     * @param connection Represents the connection object
     */
    public void release(final Connection connection) {
        if (null != connection) {
            pool.offer(connection);
        }
    }
}
