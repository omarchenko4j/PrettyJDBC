package com.github.marchenkoprojects.prettyjdbc.mapper.primitive;

import com.github.marchenkoprojects.prettyjdbc.mapper.ResultMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is a common standard mapper for retrieving a single value of <code>byte array</code> type from the first column.
 *
 * @author Oleg Marchenko
 */
public class BytesResultMapper implements ResultMapper<byte[]> {

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] map(ResultSet resultSet) throws SQLException {
        return resultSet.getBytes(1);
    }
}
