package com.github.marchenkoprojects.prettyjdbc;

import com.github.marchenkoprojects.prettyjdbc.query.Query;
import com.github.marchenkoprojects.prettyjdbc.query.scrollable_result.ReadOnlyScrollableResult;
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

/**
 * @author Oleg Marchenko
 */
public class QueryTest {

    @BeforeClass
    public static void beforeTests() {
        DatabaseInitializer.createAndInitDatabase();
    }

    @Test
    public void testQueryExecution() throws SQLException {
        try(Connection connection = JDBCUtils.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT id, original_name, year FROM films WHERE id = ?")) {

                Query query = new Query(preparedStatement);
                Assert.assertTrue(query.isActive());

                ReadOnlyScrollableResult scrollableResult = query
                        .setParameter(1, 2)
                        .execute();
                Assert.assertTrue(query.isActive());

                Assert.assertNotNull(scrollableResult);
                Assert.assertEquals(scrollableResult.getRowCount(), 1);
                Assert.assertTrue(scrollableResult.next());

                Assert.assertEquals((long) scrollableResult.getInt("id"), 2);
                Assert.assertEquals(scrollableResult.getString("original_name"), "The Lord of the Rings: The Two Towers");
                Assert.assertEquals((long) scrollableResult.getInt("year"), 2002);
            }
        }
    }

    @Test
    public void testQueryUpdateExecution() throws SQLException {
        try(Connection connection = JDBCUtils.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO films VALUES (?, ?, ?)")) {

                Query query = new Query(preparedStatement);
                Assert.assertTrue(query.isActive());

                query
                        .setParameter(1, 4)
                        .setParameter(2, "The Hobbit: An Unexpected Journey")
                        .setParameter(3, 2012)
                        .executeUpdate();
                Assert.assertTrue(query.isActive());
            }

            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT id, original_name, year FROM films WHERE id = ?")) {
                preparedStatement.setInt(1, 4);

                ResultSet resultSet = preparedStatement.executeQuery();
                Assert.assertTrue(resultSet.next());
                Assert.assertEquals((long) resultSet.getInt("id"), 4);
                Assert.assertEquals(resultSet.getString("original_name"), "The Hobbit: An Unexpected Journey");
                Assert.assertEquals((long) resultSet.getInt("year"), 2012);
            }
        }
    }

    @Test
    public void testDelegationToPreparedStatementWhenExecutingBatchedQuery() throws SQLException {
        PreparedStatement statement = Mockito.mock(PreparedStatement.class);

        Query query = new Query(statement);
        query.addBatch();
        query.addBatch();
        query.addBatch();
        Mockito.verify(statement, Mockito.times(3)).addBatch();

        query.executeBatch();
        Mockito.verify(statement).executeBatch();
    }

    @Test
    public void testBatchQueryExecution() throws SQLException {
        try(Connection connection = JDBCUtils.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO films VALUES (?, ?, ?)")) {
                Query query = new Query(preparedStatement);
                query
                        .setParameter(1, 5)
                        .setParameter(2, "The Hobbit: The Desolation of Smaug")
                        .setParameter(3, 2013)
                        .addBatch()
                        .setParameter(1, 6)
                        .setParameter(2, "The Hobbit: The Battle of the Five Armies")
                        .setParameter(3, 2014)
                        .addBatch()
                        .executeBatch();
            }

            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT id, original_name, year FROM films WHERE id = ?")) {
                preparedStatement.setInt(1, 5);
                ResultSet resultSet = preparedStatement.executeQuery();
                Assert.assertTrue(resultSet.next());
                Assert.assertEquals((long) resultSet.getInt("id"), 5);
                Assert.assertEquals(resultSet.getString("original_name"), "The Hobbit: The Desolation of Smaug");
                Assert.assertEquals((long) resultSet.getInt("year"), 2013);

                preparedStatement.setInt(1, 6);
                resultSet = preparedStatement.executeQuery();
                Assert.assertTrue(resultSet.next());
                Assert.assertEquals((long) resultSet.getInt("id"), 6);
                Assert.assertEquals(resultSet.getString("original_name"), "The Hobbit: The Battle of the Five Armies");
                Assert.assertEquals((long) resultSet.getInt("year"), 2014);
            }
        }
    }

    @Test
    public void testSettingBooleanTypeParameterByIndex() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        Query query = new Query(preparedStatement);
        query.setParameter(Mockito.anyInt(), Mockito.anyBoolean());

        Mockito.verify(preparedStatement).setBoolean(Mockito.anyInt(), Mockito.anyBoolean());
    }

    @Test
    public void testSettingByteTypeParameterByIndex() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        Query query = new Query(preparedStatement);
        query.setParameter(Mockito.anyInt(), Mockito.anyByte());

        Mockito.verify(preparedStatement).setByte(Mockito.anyInt(), Mockito.anyByte());
    }

    @Test
    public void testSettingShortTypeParameterByIndex() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        Query query = new Query(preparedStatement);
        query.setParameter(Mockito.anyInt(), Mockito.anyShort());

        Mockito.verify(preparedStatement).setShort(Mockito.anyInt(), Mockito.anyShort());
    }

    @Test
    public void testSettingIntegerTypeParameterByIndex() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        Query query = new Query(preparedStatement);
        query.setParameter(Mockito.anyInt(), Mockito.anyInt());

        Mockito.verify(preparedStatement).setInt(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    public void testSettingLongTypeParameterByIndex() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        Query query = new Query(preparedStatement);
        query.setParameter(Mockito.anyInt(), Mockito.anyLong());

        Mockito.verify(preparedStatement).setLong(Mockito.anyInt(), Mockito.anyLong());
    }

    @Test
    public void testSettingFloatTypeParameterByIndex() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        Query query = new Query(preparedStatement);
        query.setParameter(Mockito.anyInt(), Mockito.anyFloat());

        Mockito.verify(preparedStatement).setFloat(Mockito.anyInt(), Mockito.anyFloat());
    }

    @Test
    public void testSettingDoubleTypeParameterByIndex() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        Query query = new Query(preparedStatement);
        query.setParameter(Mockito.anyInt(), Mockito.anyDouble());

        Mockito.verify(preparedStatement).setDouble(Mockito.anyInt(), Mockito.anyDouble());
    }

    @Test
    public void testSettingBigDecimalTypeParameterByIndex() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        Query query = new Query(preparedStatement);
        query.setParameter(1, BigDecimal.valueOf(0));

        Mockito.verify(preparedStatement).setBigDecimal(ArgumentMatchers.anyInt(), ArgumentMatchers.any(BigDecimal.class));
    }

    @Test
    public void testSettingStringTypeParameterByIndex() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        Query query = new Query(preparedStatement);
        query.setParameter(Mockito.anyInt(), Mockito.anyString());

        Mockito.verify(preparedStatement).setString(Mockito.anyInt(), Mockito.anyString());
    }

    @Test
    public void testSettingByteArrayTypeParameterByIndex() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        Query query = new Query(preparedStatement);
        query.setParameter(1, new byte[0]);

        Mockito.verify(preparedStatement).setBytes(ArgumentMatchers.anyInt(), ArgumentMatchers.any(byte[].class));
    }

    @Test
    public void testSettingDateTypeParameterByIndex() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        Query query = new Query(preparedStatement);
        query.setParameter(1, Date.valueOf(LocalDate.now()));

        Mockito.verify(preparedStatement).setDate(ArgumentMatchers.anyInt(), ArgumentMatchers.any(Date.class));
    }

    @Test
    public void testSettingLocalDateTypeParameterByIndex() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        Query query = new Query(preparedStatement);
        query.setParameter(1, LocalDate.now());

        Mockito.verify(preparedStatement).setDate(ArgumentMatchers.anyInt(), ArgumentMatchers.any(Date.class));
    }

    @Test
    public void testSettingTimeTypeParameterByIndex() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        Query query = new Query(preparedStatement);
        query.setParameter(1, Time.valueOf(LocalTime.now()));

        Mockito.verify(preparedStatement).setTime(ArgumentMatchers.anyInt(), ArgumentMatchers.any(Time.class));
    }

    @Test
    public void testSettingLocalTimeTypeParameterByIndex() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        Query query = new Query(preparedStatement);
        query.setParameter(1, LocalTime.now());

        Mockito.verify(preparedStatement).setTime(ArgumentMatchers.anyInt(), ArgumentMatchers.any(Time.class));
    }

    @Test
    public void testSettingTimestampTypeParameterByIndex() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        Query query = new Query(preparedStatement);
        query.setParameter(1, Timestamp.valueOf(LocalDateTime.now()));

        Mockito.verify(preparedStatement).setTimestamp(ArgumentMatchers.anyInt(), ArgumentMatchers.any(Timestamp.class));
    }

    @Test
    public void testSettingLocalDateTimeTypeParameterByIndex() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        Query query = new Query(preparedStatement);
        query.setParameter(1, LocalDateTime.now());

        Mockito.verify(preparedStatement).setTimestamp(ArgumentMatchers.anyInt(), ArgumentMatchers.any(Timestamp.class));
    }

    @Test
    public void testSettingObjectTypeParameterByIndex() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        Query query = new Query(preparedStatement);
        query.setParameter(1, new Object());

        Mockito.verify(preparedStatement).setObject(ArgumentMatchers.anyInt(), ArgumentMatchers.any(Object.class));
    }

    @AfterClass
    public static void afterTests() {
        DatabaseInitializer.destroyDatabase();
    }
}
