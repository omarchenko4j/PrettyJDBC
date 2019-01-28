package com.github.marchenkoprojects.prettyjdbc.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Oleg Marchenko
 */

public final class DatabaseInitializer {

    public static void createDatabase() {
        try(Connection connection = JDBCUtils.getConnection()) {
            try(Statement statement = connection.createStatement()) {
                statement.execute(
                        "CREATE TABLE films(" +
                                "id INTEGER NOT NULL, " +
                                "original_name CHARACTER VARYING(120), " +
                                "year SMALLINT)");
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initDatabase() {
        try(Connection connection = JDBCUtils.getConnection()) {
            try(Statement statement = connection.createStatement()) {
                statement.execute("INSERT INTO films VALUES " +
                        "(1, 'The Lord of the Rings: The Fellowship of the Ring', 2001), " +
                        "(2, 'The Lord of the Rings: The Two Towers', 2002), " +
                        "(3, 'The Lord of the Rings: The Return of the King', 2003)"
                );
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createAndInitDatabase() {
        createDatabase();
        initDatabase();
    }

    public static void destroyDatabase() {
        try(Connection connection = JDBCUtils.getConnection()) {
            try(Statement statement = connection.createStatement()) {
                statement.execute("DROP TABLE films");
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private DatabaseInitializer() {
    }
}
