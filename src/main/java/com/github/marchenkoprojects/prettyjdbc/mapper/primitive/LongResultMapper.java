package com.github.marchenkoprojects.prettyjdbc.mapper.primitive;

import com.github.marchenkoprojects.prettyjdbc.mapper.ResultMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is a common standard mapper for retrieving a single value of <code>Long</code> type from the first column.
 *
 * @author Oleg Marchenko
 */
public class LongResultMapper implements ResultMapper<Long> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Long map(ResultSet resultSet) throws SQLException {
        return resultSet.getLong(1);
    }
}
