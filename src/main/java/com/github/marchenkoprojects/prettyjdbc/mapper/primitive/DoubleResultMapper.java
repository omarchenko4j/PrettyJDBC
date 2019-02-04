package com.github.marchenkoprojects.prettyjdbc.mapper.primitive;

import com.github.marchenkoprojects.prettyjdbc.mapper.ResultMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is a common standard mapper for retrieving a single value of <code>Double</code> type from the first column.
 *
 * @author Oleg Marchenko
 */
public class DoubleResultMapper implements ResultMapper<Double> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Double map(ResultSet resultSet) throws SQLException {
        return resultSet.getDouble(1);
    }
}
