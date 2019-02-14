package com.github.marchenkoprojects.prettyjdbc.query;

import com.github.marchenkoprojects.prettyjdbc.Unwrapable;
import com.github.marchenkoprojects.prettyjdbc.query.scrollable_result.CachedScrollableResult;
import com.github.marchenkoprojects.prettyjdbc.query.scrollable_result.ReadOnlyScrollableResult;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * The <code>Query</code> represents a single operation to the relational database.
 * It extends the capabilities of the standard abstraction {@link java.sql.PreparedStatement}.
 * The lifecycle of any implementation is very short and starts with the method {@link com.github.marchenkoprojects.prettyjdbc.session.Session#createQuery(String)}.
 * <br>
 * To perform a native SQL query, use the method {@link Query#execute()} which will return the result as {@link ReadOnlyScrollableResult};
 * to <code>INSERT</code>, <code>UPDATE</code> or <code>DELETE</code> the data, use the method {@link Query#executeUpdate()};
 * to perform a batched query, use the method {@link Query#addBatch()} to add a batch and {@link Query#executeBatch()} to apply it.
 *
 * @author Oleg Marchenko
 */
public class Query implements Unwrapable<PreparedStatement>, AutoCloseable, IndexedParameterQuerySetter<Query> {

    protected final PreparedStatement preparedStatement;

    public Query(PreparedStatement preparedStatement) {
        if (preparedStatement == null) {
            throw new NullPointerException("Prepared statement is null");
        }
        this.preparedStatement = preparedStatement;
    }

    /**
     * Unwrapping {@link PreparedStatement} from the specific query implementation for use outside.
     *
     * @return wrapped <code>PreparedStatement</code>
     */
    @Override
    public PreparedStatement unwrap() {
        return preparedStatement;
    }

    /**
     * Releases the internal {@link PreparedStatement} object and JDBC resources immediately,
     * instead of waiting for the automatic closing to occur.
     *
     * @exception SQLException if a database access error occurs
     * @see PreparedStatement#close()
     */
    @Override
    public void close() throws SQLException {
        preparedStatement.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query setParameter(int paramIndex, boolean value) {
        try {
            preparedStatement.setBoolean(paramIndex, value);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query setParameter(int paramIndex, byte value) {
        try {
            preparedStatement.setByte(paramIndex, value);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query setParameter(int paramIndex, short value) {
        try {
            preparedStatement.setShort(paramIndex, value);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query setParameter(int paramIndex, int value) {
        try {
            preparedStatement.setInt(paramIndex, value);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query setParameter(int paramIndex, long value) {
        try {
            preparedStatement.setLong(paramIndex, value);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query setParameter(int paramIndex, float value) {
        try {
            preparedStatement.setFloat(paramIndex, value);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query setParameter(int paramIndex, double value) {
        try {
            preparedStatement.setDouble(paramIndex, value);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query setParameter(int paramIndex, BigDecimal value) {
        try {
            preparedStatement.setBigDecimal(paramIndex, value);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query setParameter(int paramIndex, String value) {
        try {
            preparedStatement.setString(paramIndex, value);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query setParameter(int paramIndex, byte[] value) {
        try {
            preparedStatement.setBytes(paramIndex, value);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query setParameter(int paramIndex, Date value) {
        try {
            preparedStatement.setDate(paramIndex, value);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query setParameter(int paramIndex, LocalDate value) {
        Date date = null;
        if (value != null) {
            date = Date.valueOf(value);
        }
        return setParameter(paramIndex, date);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query setParameter(int paramIndex, Time value) {
        try {
            preparedStatement.setTime(paramIndex, value);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query setParameter(int paramIndex, LocalTime value) {
        Time time = null;
        if (value != null) {
            time = Time.valueOf(value);
        }
        return setParameter(paramIndex, time);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query setParameter(int paramIndex, Timestamp value) {
        try {
            preparedStatement.setTimestamp(paramIndex, value);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query setParameter(int paramIndex, LocalDateTime value) {
        Timestamp timestamp = null;
        if (value != null) {
            timestamp = Timestamp.valueOf(value);
        }
        return setParameter(paramIndex, timestamp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query setParameter(int paramIndex, Object value) {
        try {
            preparedStatement.setObject(paramIndex, value);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * Executes the SQL query and returns the {@link ReadOnlyScrollableResult} object generated by the query.
     *
     * @return a <code>ReadOnlyScrollableResult</code> object that contains the data produced by the query
     * @throws RuntimeException if a database access error occurs
     * @see ReadOnlyScrollableResult
     * @see PreparedStatement#executeQuery()
     */
    public ReadOnlyScrollableResult execute() {
        try (ResultSet result = preparedStatement.executeQuery()) {
            return new CachedScrollableResult(result);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Executes the SQL query, which must be an SQL Data Manipulation Language (DML) statement,
     * such as <code>INSERT</code>, <code>UPDATE</code> or <code>DELETE</code>;
     * or an SQL statement that returns nothing, such as a DDL statement.
     *
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements
     *         or (2) 0 for SQL statements that return nothing
     * @throws RuntimeException if a database access error occurs
     * @see PreparedStatement#executeUpdate()
     */
    public int executeUpdate() {
        try {
            return preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a set of parameters to this <code>Query</code> object's batch of commands.
     *
     * @return instance of the specific query
     * @throws RuntimeException if a database access error occurs
     * @see PreparedStatement#addBatch()
     */
    public Query addBatch() {
        try {
            preparedStatement.addBatch();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * Submits a batch of commands to the database for execution and
     * if all commands execute successfully, returns an array of update counts.
     *
     * @return an array of update counts containing one element for each
     *         command in the batch
     * @throws RuntimeException if a database access error occurs
     * @see PreparedStatement#executeBatch()
     */
    public int[] executeBatch() {
        try {
            return preparedStatement.executeBatch();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns <code>true</code> if the query is still active.
     * The query remains active until the method {@link com.github.marchenkoprojects.prettyjdbc.session.Session#close()} has been called on it
     * or certain fatal errors have not occurred.
     *
     * @return <code>true</code> if this <code>Query</code> object is still active;
     *         <code>false</code> if it is inactive
     * @see PreparedStatement#isClosed()
     */
    public boolean isActive() {
        try {
            return !preparedStatement.isClosed();
        }
        catch (SQLException e) {
            return false;
        }
    }

    /**
     * Allows to immediately close the query, protecting against possible exceptions.
     *
     * @param query the query to close
     *
     * @see Query#close()
     */
    public static void closeQuerySoftly(Query query) {
        if (query != null && query.isActive()) {
            try {
                query.close();
            }
            catch (SQLException e) {
                // Intentionally swallow the exception.
                // TODO: Add the warning after adding logging library.
            }
        }
    }
}
