package com.github.marchenkoprojects.prettyjdbc.query;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * This internal interface provides methods for setting parameter values by name in a specific {@link NamedParameterQuery}.
 *
 * @param <Q> type of return query to perform call chain
 *
 * @author Oleg Marchenko
 */
interface NamedParameterQuerySetter<Q extends NamedParameterQuery> {

    /**
     * Sets the designated parameter by name to the given Java <code>boolean</code> value.
     *
     * @param paramName the name of the parameter
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(String paramName, boolean value);

    /**
     * Sets the designated parameter by name to the given Java <code>byte</code> value.
     *
     * @param paramName the name of the parameter
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(String paramName, byte value);

    /**
     * Sets the designated parameter by name to the given Java <code>short</code> value.
     *
     * @param paramName the name of the parameter
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(String paramName, short value);

    /**
     * Sets the designated parameter by name to the given Java <code>int</code> value.
     *
     * @param paramName the name of the parameter
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(String paramName, int value);

    /**
     * Sets the designated parameter by name to the given Java <code>long</code> value.
     *
     * @param paramName the name of the parameter
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(String paramName, long value);

    /**
     * Sets the designated parameter by name to the given Java <code>float</code> value.
     *
     * @param paramName the name of the parameter
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(String paramName, float value);

    /**
     * Sets the designated parameter by name to the given Java <code>double</code> value.
     *
     * @param paramName the name of the parameter
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(String paramName, double value);

    /**
     * Sets the designated parameter by name to the given Java {@link BigDecimal} value.
     *
     * @param paramName the name of the parameter
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(String paramName, BigDecimal value);

    /**
     * Sets the designated parameter by name to the given Java <code>String</code> value.
     *
     * @param paramName the name of the parameter
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(String paramName, String value);

    /**
     * Sets the designated parameter by name to the given Java array of bytes.
     *
     * @param paramName the name of the parameter
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(String paramName, byte[] value);

    /**
     * Sets the designated parameter by name to the given Java {@link Date} value.
     *
     * @param paramName the name of the parameter
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(String paramName, Date value);

    /**
     * Sets the designated parameter by name to the given Java {@link LocalDate} value as {@link Date}.
     *
     * @param paramName the name of the parameter
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(String paramName, LocalDate value);

    /**
     * Sets the designated parameter by name to the given Java {@link Time} value.
     *
     * @param paramName the name of the parameter
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(String paramName, Time value);

    /**
     * Sets the designated parameter by name to the given Java {@link LocalTime} value as {@link Time}.
     *
     * @param paramName the name of the parameter
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(String paramName, LocalTime value);

    /**
     * Sets the designated parameter by name to the given Java {@link Timestamp} value.
     *
     * @param paramName the name of the parameter
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(String paramName, Timestamp value);

    /**
     * Sets the designated parameter by name to the given Java {@link LocalDateTime} value as {@link Timestamp}.
     *
     * @param paramName the name of the parameter
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(String paramName, LocalDateTime value);

    /**
     * Sets the value of the designated parameter by name with the given object.
     *
     * @param paramName the name of the parameter
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(String paramName, Object value);
}
