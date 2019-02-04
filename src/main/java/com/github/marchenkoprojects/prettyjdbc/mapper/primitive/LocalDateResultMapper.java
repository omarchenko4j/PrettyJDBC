package com.github.marchenkoprojects.prettyjdbc.mapper.primitive;

import com.github.marchenkoprojects.prettyjdbc.mapper.ResultMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * This is a common standard mapper for retrieving a single value of <code>LocalDate</code> type from the first column.
 *
 * @author Oleg Marchenko
 */
public class LocalDateResultMapper implements ResultMapper<LocalDate> {

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDate map(ResultSet resultSet) throws SQLException {
        Date date = resultSet.getDate(1);
        if (date == null) return null;

        return date.toLocalDate();
    }
}
