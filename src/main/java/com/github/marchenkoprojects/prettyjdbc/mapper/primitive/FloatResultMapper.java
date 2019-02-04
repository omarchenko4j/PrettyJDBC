package com.github.marchenkoprojects.prettyjdbc.mapper.primitive;

import com.github.marchenkoprojects.prettyjdbc.mapper.ResultMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is a common standard mapper for retrieving a single value of <code>Float</code> type from the first column.
 *
 * @author Oleg Marchenko
 */
public class FloatResultMapper implements ResultMapper<Float> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Float map(ResultSet resultSet) throws SQLException {
        return resultSet.getFloat(1);
    }
}
