package com.github.marchenkoprojects.prettyjdbc.query;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a SQL query with named parameters.
 *
 * @author Oleg Marchenko
 *
 * @see Query
 */
public class NamedParameterQuery extends Query implements NamedParameterQuerySetter<NamedParameterQuery> {

    private final Map<String, Integer> namedParameterToIndex;

    public NamedParameterQuery(PreparedStatement preparedStatement, List<String> parameters) {
        super(preparedStatement);

        if (parameters == null) {
            throw new NullPointerException("Parameters is null");
        }

        int size = parameters.size();
        this.namedParameterToIndex = new HashMap<>(size + 1, 1);
        for (int i = 0; i < size; i++) {
            String parameter = parameters.get(i);
            namedParameterToIndex.put(parameter, i + 1);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery addBatch() {
        super.addBatch();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(int paramIndex, boolean value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(int paramIndex, byte value) {
        super.setParameter(paramIndex, value);
        return this;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(int paramIndex, short value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(int paramIndex, int value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(int paramIndex, long value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(int paramIndex, float value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(int paramIndex, double value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(int paramIndex, BigDecimal value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(int paramIndex, String value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(int paramIndex, byte[] value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(int paramIndex, Date value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(int paramIndex, LocalDate value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(int paramIndex, Time value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(int paramIndex, LocalTime value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(int paramIndex, Timestamp value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(int paramIndex, LocalDateTime value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(int paramIndex, Object value) {
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(String paramName, boolean value) {
        int paramIndex = getParameterIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(String paramName, byte value) {
        int paramIndex = getParameterIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(String paramName, short value) {
        int paramIndex = getParameterIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(String paramName, int value) {
        int paramIndex = getParameterIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(String paramName, long value) {
        int paramIndex = getParameterIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(String paramName, float value) {
        int paramIndex = getParameterIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(String paramName, double value) {
        int paramIndex = getParameterIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(String paramName, BigDecimal value) {
        int paramIndex = getParameterIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(String paramName, String value) {
        int paramIndex = getParameterIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(String paramName, byte[] value) {
        int paramIndex = getParameterIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(String paramName, Date value) {
        int paramIndex = getParameterIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(String paramName, LocalDate value) {
        int paramIndex = getParameterIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(String paramName, Time value) {
        int paramIndex = getParameterIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(String paramName, LocalTime value) {
        int paramIndex = getParameterIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(String paramName, Timestamp value) {
        int paramIndex = getParameterIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(String paramName, LocalDateTime value) {
        int paramIndex = getParameterIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery setParameter(String paramName, Object value) {
        int paramIndex = getParameterIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    private int getParameterIndex(String paramName) {
        Integer paramIndex = namedParameterToIndex.get(paramName);
        if (paramIndex == null) {
            throw new IllegalArgumentException("Named parameter '" + paramName + "' not found");
        }
        return paramIndex;
    }
}
