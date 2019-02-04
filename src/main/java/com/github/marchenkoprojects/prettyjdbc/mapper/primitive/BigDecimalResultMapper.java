package com.github.marchenkoprojects.prettyjdbc.mapper.primitive;

import com.github.marchenkoprojects.prettyjdbc.mapper.ResultMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is a common standard mapper for retrieving a single value of <code>BigDecimal</code> type from the first column.
 *
 * @author Oleg Marchenko
 */
public class BigDecimalResultMapper implements ResultMapper<BigDecimal> {

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal map(ResultSet resultSet) throws SQLException {
        return resultSet.getBigDecimal(1);
    }
}
