package com.github.marchenkoprojects.prettyjdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This functional interface allows configure the data mapping from the {@link ResultSet} to a specific object type.
 *
 * @param <T> the type of mapping object
 *
 * @author Oleg Marchenko
 */
@FunctionalInterface
public interface ResultMapper<T> {

    /**
     * Performs mapping of data from the result set to a specific object.
     * <br>
     * <b>Note:</b> This method should describe the custom logic for mapping a data tuple into an specific object.
     *
     * @param resultSet the result set from query
     * @return a specific object, never <code>null</code>
     * @throws SQLException if a database access error occurs
     */
    T map(ResultSet resultSet) throws SQLException;
}
