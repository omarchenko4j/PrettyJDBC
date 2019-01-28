package org.prettyjdbc.core.query;

import org.prettyjdbc.core.mapper.ResultMapper;

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
 * This class represents a typed query with named parameters.
 *
 * @author Oleg Marchenko
 *
 * @see org.prettyjdbc.core.query.TypedQuery
 */

public class NamedParameterQuery<T> extends TypedQuery<T> implements NamedParameterQuerySetter<NamedParameterQuery<T>> {

    private final Map<String, Integer> paramNameToIndex;

    public NamedParameterQuery(PreparedStatement preparedStatement, List<String> parameters, Class<T> resultType) {
        super(preparedStatement, resultType);

        int parameterSize = parameters.size();
        this.paramNameToIndex = new HashMap<>(parameterSize + 1, 1);
        for (int i = 0; i < parameterSize; i++) {
            String parameter = parameters.get(i);
            paramNameToIndex.put(parameter, i + 1);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected NamedParameterQuery<T> getInstance() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery<T> setResultMapper(ResultMapper<T> resultMapper) {
        return (NamedParameterQuery<T>) super.setResultMapper(resultMapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery<T> setParameter(String paramName, boolean value) {
        int paramIndex = getParamIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery<T> setParameter(String paramName, byte value) {
        int paramIndex = getParamIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery<T> setParameter(String paramName, short value) {
        int paramIndex = getParamIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery<T> setParameter(String paramName, int value) {
        int paramIndex = getParamIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery<T> setParameter(String paramName, long value) {
        int paramIndex = getParamIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery<T> setParameter(String paramName, float value) {
        int paramIndex = getParamIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery<T> setParameter(String paramName, double value) {
        int paramIndex = getParamIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery<T> setParameter(String paramName, BigDecimal value) {
        int paramIndex = getParamIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery<T> setParameter(String paramName, String value) {
        int paramIndex = getParamIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery<T> setParameter(String paramName, byte[] value) {
        int paramIndex = getParamIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery<T> setParameter(String paramName, Date value) {
        int paramIndex = getParamIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery<T> setParameter(String paramName, LocalDate value) {
        int paramIndex = getParamIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery<T> setParameter(String paramName, Time value) {
        int paramIndex = getParamIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery<T> setParameter(String paramName, LocalTime value) {
        int paramIndex = getParamIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery<T> setParameter(String paramName, Timestamp value) {
        int paramIndex = getParamIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery<T> setParameter(String paramName, LocalDateTime value) {
        int paramIndex = getParamIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery<T> setParameter(String paramName, Object value) {
        int paramIndex = getParamIndex(paramName);
        super.setParameter(paramIndex, value);
        return this;
    }

    private int getParamIndex(String paramName) {
        Integer paramIndex = paramNameToIndex.get(paramName);
        if (paramIndex == null) {
            throw new IllegalArgumentException("");
        }
        return paramIndex;
    }
}
