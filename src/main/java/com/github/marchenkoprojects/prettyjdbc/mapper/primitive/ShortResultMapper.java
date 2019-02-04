package com.github.marchenkoprojects.prettyjdbc.mapper.primitive;

import com.github.marchenkoprojects.prettyjdbc.mapper.ResultMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is a common standard mapper for retrieving a single value of <code>Short</code> type from the first column.
 *
 * @author Oleg Marchenko
 */
public class ShortResultMapper implements ResultMapper<Short> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Short map(ResultSet resultSet) throws SQLException {
        return resultSet.getShort(1);
    }
}
