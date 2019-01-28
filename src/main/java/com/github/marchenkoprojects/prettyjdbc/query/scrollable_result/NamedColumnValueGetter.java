package com.github.marchenkoprojects.prettyjdbc.query.scrollable_result;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * This internal interface provides methods for getting the values of the columns by name in the row of the result set.
 *
 * @author Oleg Marchenko
 *
 * @see com.github.marchenkoprojects.prettyjdbc.query.scrollable_result.ReadOnlyScrollableResult
 */

interface NamedColumnValueGetter {

    /**
     * Returns the value of <code>Boolean</code> object from column by name in the current row of this result set.
     *
     * @param columnName column name in the result set
     * @return the value of <code>Boolean</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Boolean getBoolean(String columnName);

    /**
     * Returns the value of <code>Byte</code> object from column by name in the current row of this result set.
     *
     * @param columnName column name in the result set
     * @return the value of <code>Byte</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Byte getByte(String columnName);

    /**
     * Returns the value of <code>Short</code> object from column by name in the current row of this result set.
     *
     * @param columnName column name in the result set
     * @return the value of <code>Short</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Short getShort(String columnName);

    /**
     * Returns the value of <code>Integer</code> object from column by name in the current row of this result set.
     *
     * @param columnName column name in the result set
     * @return the value of <code>Integer</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Integer getInt(String columnName);

    /**
     * Returns the value of <code>Long</code> object from column by name in the current row of this result set.
     *
     * @param columnName column name in the result set
     * @return the value of <code>Long</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Long getLong(String columnName);

    /**
     * Returns the value of <code>Float</code> object from column by name in the current row of this result set.
     *
     * @param columnName column name in the result set
     * @return the value of <code>Float</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Float getFloat(String columnName);

    /**
     * Returns the value of <code>Double</code> object from column by name in the current row of this result set.
     *
     * @param columnName column name in the result set
     * @return the value of <code>Double</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Double getDouble(String columnName);

    /**
     * Returns the value of <code>BigDecimal</code> object from column by name in the current row of this result set.
     *
     * @param columnName column name in the result set
     * @return the value of <code>BigDecimal</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    BigDecimal getBigDecimal(String columnName);

    /**
     * Returns the value of <code>String</code> object from column by name in the current row of this result set.
     *
     * @param columnName column name in the result set
     * @return the value of <code>String</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    String getString(String columnName);

    /**
     * Returns the value of <code>byte</code> array from column by name in the current row of this result set.
     *
     * @param columnName column name in the result set
     * @return the value of <code>byte</code> array, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    byte[] getBytes(String columnName);

    /**
     * Returns the value of <code>Date</code> object from column by name in the current row of this result set.
     *
     * @param columnName column name in the result set
     * @return the value of <code>Date</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Date getDate(String columnName);

    /**
     * Returns the value of <code>LocalDate</code> object from column by name in the current row of this result set.
     *
     * @param columnName column name in the result set
     * @return the value of <code>LocalDate</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    LocalDate getLocalDate(String columnName);

    /**
     * Returns the value of <code>Time</code> object from column by name in the current row of this result set.
     *
     * @param columnName column name in the result set
     * @return the value of <code>Time</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Time getTime(String columnName);

    /**
     * Returns the value of <code>LocalTime</code> object from column by name in the current row of this result set.
     *
     * @param columnName column name in the result set
     * @return the value of <code>LocalTime</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    LocalTime getLocalTime(String columnName);

    /**
     * Returns the value of <code>Timestamp</code> object from column by name in the current row of this result set.
     *
     * @param columnName column name in the result set
     * @return the value of <code>Timestamp</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Timestamp getTimestamp(String columnName);

    /**
     * Returns the value of <code>LocalDateTime</code> object from column by name in the current row of this result set.
     *
     * @param columnName column name in the result set
     * @return the value of <code>LocalDateTime</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    LocalDateTime getLocalDateTime(String columnName);

    /**
     * Returns the value of <code>Object</code> from column by name in the current row of this result set.
     *
     * @param columnName column name in the result set
     * @return the value of <code>Object</code>, or
     *         <code>null</code> if the result set does not contain a column with this name
     */
    Object getObject(String columnName);
}
