package com.github.marchenkoprojects.prettyjdbc.query;

import com.github.marchenkoprojects.prettyjdbc.mapper.ResultMapper;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a typed SQL query with the ability to mapping the result in a specific object type.
 * Typed queries extend all the capabilities of {@link NamedParameterQuery} and allow to use {@link ResultMapper}
 * to convert the {@link ResultSet} to a specific object.
 * <br>
 * After the query is executed the result data tuple can be transformed into a single object or a list of objects.
 * To convert the result set to a single object, use the method {@link TypedQuery#unique()};
 * or convert to a list of objects, use the method {@link TypedQuery#list()}.
 *
 * @param <T> the specific object type
 *
 * @author Oleg Marchenko
 *
 * @see NamedParameterQuery
 * @see ResultMapper
 */
public class TypedQuery<T> extends NamedParameterQuery {

    private final Class<T> resultType;
    private ResultMapper<T> resultMapper;

    public TypedQuery(PreparedStatement preparedStatement, Class<T> resultType) {
        this(preparedStatement, Collections.emptyList(), resultType);
    }

    public TypedQuery(PreparedStatement preparedStatement, List<String> parameters, Class<T> resultType) {
        super(preparedStatement, parameters);

        if (resultType == null) {
            throw new NullPointerException("Result type is null");
        }
        this.resultType = resultType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> addBatch() {
        super.addBatch();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(int paramIndex, boolean value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(int paramIndex, byte value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(int paramIndex, short value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(int paramIndex, int value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(int paramIndex, long value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(int paramIndex, float value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(int paramIndex, double value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(int paramIndex, BigDecimal value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(int paramIndex, String value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(int paramIndex, byte[] value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(int paramIndex, Date value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(int paramIndex, LocalDate value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(int paramIndex, Time value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(int paramIndex, LocalTime value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(int paramIndex, Timestamp value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(int paramIndex, LocalDateTime value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(int paramIndex, Object value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(String paramName, boolean value) {
        super.setParameter(paramName, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(String paramName, byte value) {
        super.setParameter(paramName, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(String paramName, short value) {
        super.setParameter(paramName, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(String paramName, int value) {
        super.setParameter(paramName, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(String paramName, long value) {
        super.setParameter(paramName, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(String paramName, float value) {
        super.setParameter(paramName, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(String paramName, double value) {
        super.setParameter(paramName, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(String paramName, BigDecimal value) {
        super.setParameter(paramName, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(String paramName, String value) {
        super.setParameter(paramName, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(String paramName, byte[] value) {
        super.setParameter(paramName, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(String paramName, Date value) {
        super.setParameter(paramName, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(String paramName, LocalDate value) {
        super.setParameter(paramName, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(String paramName, Time value) {
        super.setParameter(paramName, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(String paramName, LocalTime value) {
        super.setParameter(paramName, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(String paramName, Timestamp value) {
        super.setParameter(paramName, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(String paramName, LocalDateTime value) {
        super.setParameter(paramName, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedQuery<T> setParameter(String paramName, Object value) {
        super.setParameter(paramName, value);
        return this;
    }

    /**
     * Sets the result mapper for transforming to specific typed object.
     *
     * @param resultMapper the specific result mapper
     * @return instance of this query
     */
    public TypedQuery<T> setResultMapper(ResultMapper<T> resultMapper) {
        if (resultMapper == null) {
            throw new NullPointerException("Result mapper is null");
        }
        this.resultMapper = resultMapper;
        return this;
    }

    /**
     * Retrieves a single tuple from the {@link ResultSet} and transforms it into a specific object.
     *
     * @return a single specific object or <code>null</code>, if the result set is empty
     * @throws IllegalStateException if the result mapper is null
     * @throws RuntimeException if a database access error occurs
     *  or this method is called when the session connection is closed
     */
    public T unique() {
        checkMapperPresent();

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultMapper.map(resultSet);
            }
            return null;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all tuples from the {@link ResultSet} and transforms it into a list of specific objects.
     *
     * @return a list of specific objects
     * @throws IllegalStateException if the result mapper is null
     * @throws RuntimeException if a database access error occurs
     *  or this method is called when the session connection is closed
     */
    public List<T> list() {
        checkMapperPresent();

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            List<T> list = new ArrayList<>(32);
            while (resultSet.next()) {
                list.add(resultMapper.map(resultSet));
            }
            return list;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkMapperPresent() {
        if (resultMapper == null) {
            throw new IllegalStateException("Result mapper for type '" + resultType.getSimpleName() + "' not found");
        }
    }
}
