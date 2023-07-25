package com.amazon.dao.impl;

import com.amazon.exception.DBException;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class DataSourceDBConnection {

    private static final DataSourceDBConnection DATA_SOURCE_DB_CONNECTION = new DataSourceDBConnection();
    private final BasicDataSource dataSource;
    private DataSourceDBConnection() {
        dataSource = new BasicDataSource();
    }

    public static DataSourceDBConnection getInstance() {
        return  DATA_SOURCE_DB_CONNECTION;
    }

    public DataSource getDataSource() {
        final String filePath = "C:\\Users\\krith\\IdeaProjects\\AmazonWeb1\\src\\main\\resources\\ApplicationProperties";
        final Properties properties = new Properties();

        try(final FileInputStream file = new FileInputStream( filePath)) {
            properties.load(file);
            dataSource.setDriverClassName(properties.getProperty("classname"));
            dataSource.setUrl(properties.getProperty("database_url"));
            dataSource.setUsername(properties.getProperty("user"));
            dataSource.setPassword(properties.getProperty("password"));
            dataSource.setMaxIdle(20);
            dataSource.setMinIdle(10);

            return dataSource;
        } catch (IOException exception) {
            throw new DBException(exception.getMessage());
        }
    }
}
