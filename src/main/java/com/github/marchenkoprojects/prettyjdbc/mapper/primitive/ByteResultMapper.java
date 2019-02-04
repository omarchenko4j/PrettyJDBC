package com.github.marchenkoprojects.prettyjdbc.mapper.primitive;

import com.github.marchenkoprojects.prettyjdbc.mapper.ResultMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is a common standard mapper for retrieving a single value of <code>Byte</code> type from the first column.
 *
 * @author Oleg Marchenko
 */
public class ByteResultMapper implements ResultMapper<Byte> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte map(ResultSet resultSet) throws SQLException {
        return resultSet.getByte(1);
    }
}
