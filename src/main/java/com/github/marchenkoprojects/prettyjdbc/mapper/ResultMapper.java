package com.github.marchenkoprojects.prettyjdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
     * Retrieves a single tuple from the {@link ResultSet} and transforms it into a specific object.
     *
     * @param resultSet the result set from query
     * @return a specific object or <code>null</code>, if the result set is empty
     * @throws SQLException if a database access error occurs
     */
    default T unique(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return map(resultSet);
        }
        return null;
    }

    /**
     * Retrieves all tuples from the {@link ResultSet} and transforms it into a list of specific objects.
     *
     * @param resultSet the result set from query
     * @return a list of specific objects
     * @throws SQLException if a database access error occurs
     */
    default List<T> list(ResultSet resultSet) throws SQLException {
        List<T> resultList = new ArrayList<>(32);
        while (resultSet.next()) {
            resultList.add(map(resultSet));
        }
        return resultList;
    }

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
