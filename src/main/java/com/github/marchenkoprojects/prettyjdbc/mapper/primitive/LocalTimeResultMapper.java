package com.github.marchenkoprojects.prettyjdbc.mapper.primitive;

import com.github.marchenkoprojects.prettyjdbc.mapper.ResultMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

/**
 * This is a common standard mapper for retrieving a single value of <code>LocalTime</code> type from the first column.
 *
 * @author Oleg Marchenko
 */
@Deprecated
public class LocalTimeResultMapper implements ResultMapper<LocalTime> {

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalTime map(ResultSet resultSet) throws SQLException {
        Time time = resultSet.getTime(1);
        if (time == null) return null;

        return time.toLocalTime();
    }
}
