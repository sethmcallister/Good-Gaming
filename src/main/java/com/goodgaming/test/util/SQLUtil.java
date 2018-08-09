package com.goodgaming.test.util;

import org.bukkit.Bukkit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class SQLUtil {
    private static final String jdbcDriver = "com.mysql.jdbc.Driver";
    private static final String dbAddress = "jdbc:mysql://localhost:3306/";
    private static final Logger LOGGER = LoggerFactory.getLogger(SQLUtil.class);

    public static void createDatabase(final String dbName) {
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName(jdbcDriver);

            LOGGER.info(String.format("Connecting to MySQL %s", dbAddress));
            connection = DriverManager.getConnection(dbAddress, "root", "");
            LOGGER.info(String.format("Connected to MySQL %s", dbAddress));

            LOGGER.info("Preparing SQL statement");
            statement = connection.createStatement();
            String sql = String.format("CREATE DATABASE %s", dbName);
            LOGGER.info("Executing SQL statement");

            statement.execute(sql);
            LOGGER.info(String.format("Executed SQL statement, created database %s", dbName));
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
    }

    public static boolean doesDatabaseExist(final String dbName) {
        Connection connection = null;

        try {
            Class.forName(jdbcDriver);
            Bukkit.getLogger().info(String.format("Connecting to MySQL %s", dbAddress));
            connection = DriverManager.getConnection(dbAddress, "root", "");

            ResultSet resultSet = connection.getMetaData().getCatalogs();

            while (resultSet.next()) {
                String databaseName = resultSet.getString(1);
                if (databaseName.equalsIgnoreCase(dbName))
                    return true;
            }

            resultSet.close();
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return false;
    }
}
