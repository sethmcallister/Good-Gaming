package com.goodgaming.test.util;

import com.goodgaming.test.dto.Economy;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.UUID;

public class HikariUtil {
    private static final String configFile = "/db.properties";
    private static final HikariConfig config = new HikariConfig(configFile);
    private static final HikariDataSource dataSource = new HikariDataSource(config);
    private static final Logger LOGGER = LoggerFactory.getLogger(HikariUtil.class);

    public static void insert(final String tableName, final UUID uuid) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            LOGGER.info("Setting up connection from data source");
            connection = dataSource.getConnection();

            LOGGER.info("Setting up prepared statement");
            String sql = "INSERT INTO " + tableName + "(uuid, balance)" + " VALUES ('" + uuid.toString() + "', 1)";
            statement = connection.prepareStatement(sql);

            LOGGER.info("Executing SQL statement, inserting uuid and balance");
            statement.execute(sql);
        } catch (SQLException e) {
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

    public static void update(final String tableName, final UUID uuid, final int balance) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            LOGGER.info("Setting up connection from data source");
            connection = dataSource.getConnection();

            LOGGER.info("Setting up prepared statement");
            String sql = "UPDATE " + tableName + " SET balance = " + balance + " WHERE uuid = '" + uuid.toString() + "'";
            statement = connection.prepareStatement(sql);

            LOGGER.info("Executing SQL statement, updating balance");
            statement.execute(sql);
        } catch (SQLException e) {
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

    public static Economy select(final String tableName, final UUID uuid) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            LOGGER.info("Setting up connection from data source");
            connection = dataSource.getConnection();

            LOGGER.info("Setting up prepared statement");
            String sql = "SELECT uuid, balance FROM " + tableName +
                    " WHERE uuid = '" + uuid.toString() + "'";
            statement = connection.prepareStatement(sql);

            LOGGER.info("Executing SQL statement, and returning ResultSet");
            ResultSet resultSet = statement.executeQuery(sql);

            UUID uniqueId = null;
            int balance = 0;
            while (resultSet.next()) {
                uniqueId = UUID.fromString(resultSet.getString("uuid"));
                LOGGER.info("uniqueId from SQL == " + uniqueId);
                balance = resultSet.getInt("balance");
                LOGGER.info("balance from SQL == " + balance);
            }
            resultSet.close();
            if (uniqueId == null) {
                return new Economy(uuid, -1);
            }
            return new Economy(uniqueId, balance);
        } catch (SQLException e) {
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
        return new Economy(uuid, -1);
    }

    public static void createTable(final String tableName) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            LOGGER.info("Setting up connection from data source");
            connection = dataSource.getConnection();

            LOGGER.info("Setting up prepared statement");
            String sql = String.format("CREATE TABLE %s ", tableName) +
                    "(id INT AUTO_INCREMENT, " +
                    "uuid VARCHAR(36) UNIQUE, " +
                    "balance INT, " +
                    "PRIMARY KEY (id))";
            statement = connection.prepareStatement(sql);

            LOGGER.info("Executing SQL statement, creating table, with values (id int primary key, uuid, balance int)");
            statement.execute();
        } catch (SQLException e) {
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

    public static boolean doesTableExist(final String tableName) {
        Connection connection = null;

        try {
            LOGGER.info("Setting up connection from data source");
            connection = dataSource.getConnection();

            LOGGER.info("Setting up prepared statement");
            ResultSet resultSet = connection.getMetaData().getTables(null, null, tableName, null);

            LOGGER.info("Checking result set iterator");
            while (resultSet.next()) {
                String name = resultSet.getString("TABLE_NAME");
                if (name != null && name.equalsIgnoreCase(tableName)) {
                    return true;
                }
            }

            resultSet.close();
        } catch (SQLException e) {
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