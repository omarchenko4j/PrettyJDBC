package com.github.marchenkoprojects.prettyjdbc.mapper.primitive;

import com.github.marchenkoprojects.prettyjdbc.mapper.ResultMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

/**
 * This is a common standard mapper for retrieving a single value of <code>Time</code> type from the first column.
 *
 * @author Oleg Marchenko
 */
@Deprecated
public class TimeResultMapper implements ResultMapper<Time> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Time map(ResultSet resultSet) throws SQLException {
        return resultSet.getTime(1);
    }
}
