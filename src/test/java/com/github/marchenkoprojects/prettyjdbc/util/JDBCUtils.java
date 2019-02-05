package com.github.marchenkoprojects.prettyjdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Oleg Marchenko
 */
public final class JDBCUtils {
    public static final String DATABASE_NAME = "test_db";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:hsqldb:mem:" + DATABASE_NAME, "SA", "");
        }
        catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static boolean getAutoCommit(Connection connection) {
        try {
            return connection.getAutoCommit();
        }
        catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static boolean isConnectionClosed(Connection connection) {
        try {
            return connection.isClosed();
        }
        catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static PreparedStatement getPreparedStatement(Connection connection, String sql) {
        try {
            return connection.prepareStatement(sql);
        }
        catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void throwException() {
        throw new RuntimeException();
    }

    private JDBCUtils() {
    }
}
