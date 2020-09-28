package com.github.marchenkoprojects.prettyjdbc.mapper.primitive;

import com.github.marchenkoprojects.prettyjdbc.mapper.ResultMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is a common standard mapper for retrieving a single value of <code>Boolean</code> type from the first column.
 *
 * @author Oleg Marchenko
 */
@Deprecated
public class BooleanResultMapper implements ResultMapper<Boolean> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean map(ResultSet resultSet) throws SQLException {
        return resultSet.getBoolean(1);
    }
}
