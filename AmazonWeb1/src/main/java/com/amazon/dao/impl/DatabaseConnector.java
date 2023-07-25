package com.amazon.dao.impl;

import com.amazon.exception.DatabaseException;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConnector {

    private static final DatabaseConnector DATA_SOURCE_DB_CONNECTION = new DatabaseConnector();
    private final BasicDataSource dataSource;

    private DatabaseConnector() {
        dataSource = new BasicDataSource();
    }

    /**
     * <p>
     * Method to provide access to the single instance
     * </p>
     *
     * @return {@link DatabaseConnector}
     */
    public static DatabaseConnector getInstance() {
        return DATA_SOURCE_DB_CONNECTION;
    }

    /**
     * <p>
     * This method is used to obtain the data source for database connection
     * </p>
     *
     * @return Describes the data source object
     */
    public DataSource getDataSource() {
        final String filePath = "C:\\Users\\krith\\IdeaProjects\\AmazonWeb1\\src\\main\\resources\\ApplicationProperties";
        final Properties properties = new Properties();

        try (final FileInputStream file = new FileInputStream(filePath)) {
            properties.load(file);
            dataSource.setDriverClassName(properties.getProperty("classname"));
            dataSource.setUrl(properties.getProperty("database_url"));
            dataSource.setUsername(properties.getProperty("user"));
            dataSource.setPassword(properties.getProperty("password"));
            dataSource.setMaxIdle(Integer.parseInt(properties.getProperty("max")));
            dataSource.setMinIdle(Integer.parseInt(properties.getProperty("min")));

            return dataSource;
        } catch (IOException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }
}
