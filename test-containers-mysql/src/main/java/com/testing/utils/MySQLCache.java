package com.testing.utils;

import com.mysql.cj.log.Slf4JLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class MySQLCache {
    private static Connection conn;
    private static Statement statement;

    private static final Logger LOG = LoggerFactory.getLogger(MySQLCache.class);

    public MySQLCache (String url, String user, String pass) {
        try {
            if(conn == null) {
                conn = DriverManager.getConnection(url, user, pass);
                statement = conn.createStatement();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int insert(String table, String... fieldValue) throws SQLException {
        if (fieldValue.length % 2 != 0) {
            LOG.error("Invalid number of parameters");
            return -1;
        }
        String fields = "(" + fieldValue[0] + "," + fieldValue[2] + ")";
        String values = "('" + fieldValue[1] + "', " + fieldValue[3] + ")";
        return statement.executeUpdate("INSERT INTO " + table + " " + fields + " VALUES " + values + ";");
    }

    public ResultSet get(String table, String key, String value) throws SQLException {
        return statement.executeQuery("SELECT * FROM " + table + " WHERE " + key + "='" + value + "';");
    }

    public int update(String table, String... fieldValue) throws SQLException {
        int size = fieldValue.length;
        if (size % 2 != 0) {
            LOG.error("Invalid number of parameters");
            return -1;
        }
        return statement.executeUpdate("UPDATE " + table + " SET " + fieldValue[0] + "='" + fieldValue[1]
                + "' WHERE " + fieldValue[size - 2] + "='" + fieldValue[size - 1] + "';");
    }

    public int delete(String table, String key, String value) throws SQLException {
        return statement.executeUpdate("DELETE FROM " + table + " WHERE " + key + "='" + value + "';");
    }
}
