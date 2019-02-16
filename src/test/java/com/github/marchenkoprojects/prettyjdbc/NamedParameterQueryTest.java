package com.github.marchenkoprojects.prettyjdbc;

import com.github.marchenkoprojects.prettyjdbc.query.NamedParameterQuery;
import com.github.marchenkoprojects.prettyjdbc.query.scrollable_result.ReadOnlyScrollableResult;
import com.github.marchenkoprojects.prettyjdbc.session.Session;
import com.github.marchenkoprojects.prettyjdbc.util.DatabaseInitializer;
import com.github.marchenkoprojects.prettyjdbc.util.JDBCUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;

/**
 * @author Oleg Marchenko
 */
public class NamedParameterQueryTest {

    @BeforeClass
    public static void beforeTests() {
        DatabaseInitializer.createAndInitDatabase();
    }

    @Test
    public void testQueryExecutionWithNamedParametersInClassicStyle() {
        Connection connection = JDBCUtils.getConnection();
        try(Session session = SessionFactory.newSession(connection)) {
            ReadOnlyScrollableResult scrollableResult = session
                    .createQuery("SELECT id, original_name, year FROM films WHERE id = :filmId")
                    .setParameter("filmId", 1)
                    .execute();
            Assert.assertNotNull(scrollableResult);
            Assert.assertTrue(scrollableResult.next());
            Assert.assertEquals((int) scrollableResult.getInt("id"), 1);
            Assert.assertEquals(scrollableResult.getString("original_name"), "The Lord of the Rings: The Fellowship of the Ring");
            Assert.assertEquals((int) scrollableResult.getInt("year"), 2001);
        }
    }

    @Test
    public void testQueryExecutionWithNamedParametersInAdditionalStyle() {
        Connection connection = JDBCUtils.getConnection();
        try(Session session = SessionFactory.newSession(connection)) {
            ReadOnlyScrollableResult scrollableResult = session
                    .createQuery("SELECT * FROM films WHERE year >= :{year} OFFSET :{offset} LIMIT :{limit}")
                    .setParameter("year", 2002)
                    .setParameter("offset", 0)
                    .setParameter("limit", 5)
                    .execute();
            Assert.assertNotNull(scrollableResult);
            Assert.assertTrue(scrollableResult.next());
            Assert.assertEquals(scrollableResult.getRowCount(), 2);
        }
    }

    @Test
    public void testSettingBooleanTypeParameterByName() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        NamedParameterQuery query = new NamedParameterQuery(preparedStatement, Collections.singletonList("column_1"));
        query.setParameter("column_1", true);

        Mockito.verify(preparedStatement).setBoolean(ArgumentMatchers.eq(1), ArgumentMatchers.anyBoolean());
    }

    @Test
    public void testSettingByteTypeParameterByName() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        NamedParameterQuery query = new NamedParameterQuery(preparedStatement, Collections.singletonList("column_1"));
        query.setParameter("column_1", (byte) 100);

        Mockito.verify(preparedStatement).setByte(ArgumentMatchers.eq(1), ArgumentMatchers.anyByte());
    }

    @Test
    public void testSettingShortTypeParameterByName() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        NamedParameterQuery query = new NamedParameterQuery(preparedStatement, Collections.singletonList("column_1"));
        query.setParameter("column_1", (short) 1000);

        Mockito.verify(preparedStatement).setShort(ArgumentMatchers.eq(1), ArgumentMatchers.anyShort());
    }

    @Test
    public void testSettingIntegerTypeParameterByName() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        NamedParameterQuery query = new NamedParameterQuery(preparedStatement, Collections.singletonList("column_1"));
        query.setParameter("column_1", 10000);

        Mockito.verify(preparedStatement).setInt(ArgumentMatchers.eq(1), ArgumentMatchers.anyInt());
    }

    @Test
    public void testSettingLongTypeParameterByName() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        NamedParameterQuery query = new NamedParameterQuery(preparedStatement, Collections.singletonList("column_1"));
        query.setParameter("column_1", 100000L);

        Mockito.verify(preparedStatement).setLong(ArgumentMatchers.eq(1), ArgumentMatchers.anyLong());
    }

    @Test
    public void testSettingFloatTypeParameterByName() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        NamedParameterQuery query = new NamedParameterQuery(preparedStatement, Collections.singletonList("column_1"));
        query.setParameter("column_1", 100.001F);

        Mockito.verify(preparedStatement).setFloat(ArgumentMatchers.eq(1), ArgumentMatchers.anyFloat());
    }

    @Test
    public void testSettingDoubleTypeParameterByName() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        NamedParameterQuery query = new NamedParameterQuery(preparedStatement, Collections.singletonList("column_1"));
        query.setParameter("column_1", 1000.0001);

        Mockito.verify(preparedStatement).setDouble(ArgumentMatchers.eq(1), ArgumentMatchers.anyDouble());
    }

    @Test
    public void testSettingBigDecimalTypeParameterByName() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        NamedParameterQuery query = new NamedParameterQuery(preparedStatement, Collections.singletonList("column_1"));
        query.setParameter("column_1", BigDecimal.ONE);

        Mockito.verify(preparedStatement).setBigDecimal(ArgumentMatchers.eq(1), ArgumentMatchers.any(BigDecimal.class));
    }

    @Test
    public void testSettingStringTypeParameterByName() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        NamedParameterQuery query = new NamedParameterQuery(preparedStatement, Collections.singletonList("column_1"));
        query.setParameter("column_1", "column_value_1");

        Mockito.verify(preparedStatement).setString(ArgumentMatchers.eq(1), ArgumentMatchers.anyString());
    }

    @Test
    public void testSettingByteArrayTypeParameterByName() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        NamedParameterQuery query = new NamedParameterQuery(preparedStatement, Collections.singletonList("column_1"));
        query.setParameter("column_1", new byte[0]);

        Mockito.verify(preparedStatement).setBytes(ArgumentMatchers.eq(1), ArgumentMatchers.any(byte[].class));
    }

    @Test
    public void testSettingDateTypeParameterByName() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        NamedParameterQuery query = new NamedParameterQuery(preparedStatement, Collections.singletonList("column_1"));
        query.setParameter("column_1", Date.valueOf(LocalDate.now()));

        Mockito.verify(preparedStatement).setDate(ArgumentMatchers.eq(1), ArgumentMatchers.any(Date.class));
    }

    @Test
    public void testSettingLocalDateTypeParameterByName() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        NamedParameterQuery query = new NamedParameterQuery(preparedStatement, Collections.singletonList("column_1"));
        query.setParameter("column_1", LocalDate.now());

        Mockito.verify(preparedStatement).setDate(ArgumentMatchers.eq(1), ArgumentMatchers.any(Date.class));
    }

    @Test
    public void testSettingTimeTypeParameterByName() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        NamedParameterQuery query = new NamedParameterQuery(preparedStatement, Collections.singletonList("column_1"));
        query.setParameter("column_1", Time.valueOf(LocalTime.now()));

        Mockito.verify(preparedStatement).setTime(ArgumentMatchers.eq(1), ArgumentMatchers.any(Time.class));
    }

    @Test
    public void testSettingLocalTimeTypeParameterByName() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        NamedParameterQuery query = new NamedParameterQuery(preparedStatement, Collections.singletonList("column_1"));
        query.setParameter("column_1", LocalTime.now());

        Mockito.verify(preparedStatement).setTime(ArgumentMatchers.eq(1), ArgumentMatchers.any(Time.class));
    }

    @Test
    public void testSettingTimestampTypeParameterByName() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        NamedParameterQuery query = new NamedParameterQuery(preparedStatement, Collections.singletonList("column_1"));
        query.setParameter("column_1", Timestamp.valueOf(LocalDateTime.now()));

        Mockito.verify(preparedStatement).setTimestamp(ArgumentMatchers.eq(1), ArgumentMatchers.any(Timestamp.class));
    }

    @Test
    public void testSettingLocalDateTimeTypeParameterByName() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        NamedParameterQuery query = new NamedParameterQuery(preparedStatement, Collections.singletonList("column_1"));
        query.setParameter("column_1", LocalDateTime.now());

        Mockito.verify(preparedStatement).setTimestamp(ArgumentMatchers.eq(1), ArgumentMatchers.any(Timestamp.class));
    }

    @Test
    public void testSettingObjectTypeParameterByName() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        NamedParameterQuery query = new NamedParameterQuery(preparedStatement, Collections.singletonList("column_1"));
        query.setParameter("column_1", new Object());

        Mockito.verify(preparedStatement).setObject(ArgumentMatchers.eq(1), ArgumentMatchers.any(Object.class));
    }

    @AfterClass
    public static void afterTests() {
        DatabaseInitializer.destroyDatabase();
    }
}
