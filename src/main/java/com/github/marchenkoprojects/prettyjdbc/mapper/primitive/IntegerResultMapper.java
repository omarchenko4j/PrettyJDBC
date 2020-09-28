package com.github.marchenkoprojects.prettyjdbc.mapper.primitive;

import com.github.marchenkoprojects.prettyjdbc.mapper.ResultMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is a common standard mapper for retrieving a single value of <code>Integer</code> type from the first column.
 *
 * @author Oleg Marchenko
 */
@Deprecated
public class IntegerResultMapper implements ResultMapper<Integer> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer map(ResultSet resultSet) throws SQLException {
        return resultSet.getInt(1);
    }
}
