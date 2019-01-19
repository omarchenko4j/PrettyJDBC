package org.prettyjdbc.core.query;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * This internal interface provides methods for setting parameter values in a specific {@link org.prettyjdbc.core.query.AbstractQuery}.
 *
 * @param <Q> type of return query to perform call chain
 *
 * @author Oleg Marchenko
 *
 * @see org.prettyjdbc.core.query.AbstractQuery
 */

interface IndexedParameterQuerySetter<Q> {

    /**
     * Sets the designated parameter to the given Java <code>boolean</code> value.
     *
     * @param paramIndex the index of the parameter which must begin with 1
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(int paramIndex, boolean value);

    /**
     * Sets the designated parameter to the given Java <code>byte</code> value.
     *
     * @param paramIndex the index of the parameter which must begin with 1
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(int paramIndex, byte value);

    /**
     * Sets the designated parameter to the given Java <code>short</code> value.
     *
     * @param paramIndex the index of the parameter which must begin with 1
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(int paramIndex, short value);

    /**
     * Sets the designated parameter to the given Java <code>int</code> value.
     *
     * @param paramIndex the index of the parameter which must begin with 1
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(int paramIndex, int value);

    /**
     * Sets the designated parameter to the given Java <code>long</code> value.
     *
     * @param paramIndex the index of the parameter which must begin with 1
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(int paramIndex, long value);

    /**
     * Sets the designated parameter to the given Java <code>float</code> value.
     *
     * @param paramIndex the index of the parameter which must begin with 1
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(int paramIndex, float value);

    /**
     * Sets the designated parameter to the given Java <code>double</code> value.
     *
     * @param paramIndex the index of the parameter which must begin with 1
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(int paramIndex, double value);

    /**
     * Sets the designated parameter to the given Java <code>java.math.BigDecimal</code> value.
     *
     * @param paramIndex the index of the parameter which must begin with 1
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(int paramIndex, BigDecimal value);

    /**
     * Sets the designated parameter to the given Java <code>String</code> value.
     *
     * @param paramIndex the index of the parameter which must begin with 1
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(int paramIndex, String value);

    /**
     * Sets the designated parameter to the given Java array of bytes.
     *
     * @param paramIndex the index of the parameter which must begin with 1
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(int paramIndex, byte[] value);

    /**
     * Sets the designated parameter to the given Java <code>java.sql.Date</code> value.
     *
     * @param paramIndex the index of the parameter which must begin with 1
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(int paramIndex, Date value);

    /**
     * Sets the designated parameter to the given Java <code>java.time.LocalDate</code> value as <code>java.sql.Date</code>.
     *
     * @param paramIndex the index of the parameter which must begin with 1
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(int paramIndex, LocalDate value);

    /**
     * Sets the designated parameter to the given Java <code>java.sql.Time</code> value.
     *
     * @param paramIndex the index of the parameter which must begin with 1
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(int paramIndex, Time value);

    /**
     * Sets the designated parameter to the given Java <code>java.time.LocalTime</code> value as <code>java.sql.Time</code>.
     *
     * @param paramIndex the index of the parameter which must begin with 1
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(int paramIndex, LocalTime value);

    /**
     * Sets the designated parameter to the given Java <code>java.sql.Timestamp</code> value.
     *
     * @param paramIndex the index of the parameter which must begin with 1
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(int paramIndex, Timestamp value);

    /**
     * Sets the designated parameter to the given Java <code>java.time.LocalDateTime</code> value as <code>java.sql.Timestamp</code>.
     *
     * @param paramIndex the index of the parameter which must begin with 1
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(int paramIndex, LocalDateTime value);

    /**
     * Sets the value of the designated parameter with the given object.
     *
     * @param paramIndex the index of the parameter which must begin with 1
     * @param value the parameter value
     * @return instance of this query
     */
    Q setParameter(int paramIndex, Object value);
}
