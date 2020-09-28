package com.github.marchenkoprojects.prettyjdbc.mapper.primitive;

import com.github.marchenkoprojects.prettyjdbc.mapper.ResultMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This is a common standard mapper for retrieving a single value of <code>LocalDateTime</code> type from the first column.
 *
 * @author Oleg Marchenko
 */
@Deprecated
public class LocalDateTimeResultMapper implements ResultMapper<LocalDateTime> {

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime map(ResultSet resultSet) throws SQLException {
        Timestamp timestamp = resultSet.getTimestamp(1);
        if (timestamp == null) return null;

        return timestamp.toLocalDateTime();
    }
}
