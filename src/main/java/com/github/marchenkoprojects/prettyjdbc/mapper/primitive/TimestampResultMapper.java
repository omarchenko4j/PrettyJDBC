package com.github.marchenkoprojects.prettyjdbc.mapper.primitive;

import com.github.marchenkoprojects.prettyjdbc.mapper.ResultMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * This is a common standard mapper for retrieving a single value of <code>Timestamp</code> type from the first column.
 *
 * @author Oleg Marchenko
 */
public class TimestampResultMapper implements ResultMapper<Timestamp> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp map(ResultSet resultSet) throws SQLException {
        return resultSet.getTimestamp(1);
    }
}
