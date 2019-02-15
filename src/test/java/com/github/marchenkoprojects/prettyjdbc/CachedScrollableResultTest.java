package com.github.marchenkoprojects.prettyjdbc;

import com.github.marchenkoprojects.prettyjdbc.query.scrollable_result.CachedScrollableResult;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author Oleg Marchenko
 */
public class CachedScrollableResultTest {

    @Test
    public void testEmptyScrollableResult() throws SQLException {
        ResultSetMetaData resultSetMetaData = Mockito.mock(ResultSetMetaData.class);
        Mockito.when(resultSetMetaData.getColumnCount()).thenReturn(1);
        Mockito.when(resultSetMetaData.getColumnName(1)).thenReturn("first_column");

        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.getMetaData()).thenReturn(resultSetMetaData);

        CachedScrollableResult scrollableResult = new CachedScrollableResult(resultSet);
        Assert.assertTrue(scrollableResult.isBeforeFirst());
        Assert.assertEquals(scrollableResult.getRowCount(), 0);
        Assert.assertTrue(scrollableResult.isEmpty());
        Assert.assertFalse(scrollableResult.next());
    }
}
