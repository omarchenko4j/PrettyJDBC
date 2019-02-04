package com.github.marchenkoprojects.prettyjdbc.mapper.primitive;

import com.github.marchenkoprojects.prettyjdbc.mapper.ResultMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is a common standard mapper for retrieving a single value of <code>Date</code> type from the first column.
 *
 * @author Oleg Marchenko
 */
public class DateResultMapper implements ResultMapper<Date> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Date map(ResultSet resultSet) throws SQLException {
        return resultSet.getDate(1);
    }
}
