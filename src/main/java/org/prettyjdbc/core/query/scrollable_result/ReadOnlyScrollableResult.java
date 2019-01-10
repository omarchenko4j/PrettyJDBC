package org.prettyjdbc.core.query.scrollable_result;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Interface provides read-only access for cached scrollable result by column index or name.
 *
 * @author Oleg Marchenko
 */

public interface ReadOnlyScrollableResult {

    /**
     * Moves the cursor to the previous row and returns <tt>true</tt> if the cursor after the first row.
     *
     * @return <tt>true</tt> if the cursor after the first row
     */
    boolean previous();

    /**
     * Moves the cursor to the next row and returns <tt>true</tt> if the cursor before the last row.
     *
     * @return <tt>true</tt> if the cursor before the last row
     */
    boolean next();

    /**
     * Returns <tt>true</tt> if this result set contains no rows.
     *
     * @return <tt>true</tt> if this result set contains no rows
     */
    boolean isEmpty();

    /**
     * Returns <tt>true</tt> if the cursor is before the first row.
     *
     * @return <tt>true</tt> if the cursor is before the first row
     */
    boolean isBeforeFirst();

    /**
     * Returns <tt>true</tt> if the cursor is after the last row.
     *
     * @return <tt>true</tt> if the cursor is after the last row
     */
    boolean isAfterLast();

    /**
     * Returns <tt>true</tt> if the cursor is on the first row.
     *
     * @return <tt>true</tt> if the cursor is on the first row
     */
    boolean isFirst();

    /**
     * Returns <tt>true</tt> if the cursor is on the last row.
     *
     * @return <tt>true</tt> if the cursor is on the last row
     */
    boolean isLast();

    /**
     * Moves the cursor to the front of result set, just before the first row.
     */
    void beforeFirst();

    /**
     * Moves the cursor to the back of result set, just after the last row.
     */
    void afterLast();

    /**
     * Moves the cursor to the first row and returns <tt>true</tt> if the cursor is on a valid row.
     *
     * @return <tt>true</tt> if the cursor is on a valid row
     */
    boolean first();

    /**
     * Moves the cursor to the last row and returns <tt>true</tt> if the cursor is on a valid row.
     *
     * @return <tt>true</tt> if the cursor is on a valid row
     */
    boolean last();

    /**
     * Returns the current row number. The first row is number 1 and las row is number {@link #getRowCount()} - 1.
     *
     * @return the current row number
     */
    int getRowIndex();

    /**
     * Returns the number of rows in this result set.
     *
     * @return the number of rows in this result set
     */
    int getRowCount();

    /**
     * Returns the value of <code>boolean</code> type from column by index.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>boolean</code> type
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    boolean getBoolean(int columnIndex);

    /**
     * Returns the value of <code>byte</code> type from column by index.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>byte</code> type
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    byte getByte(int columnIndex);

    /**
     * Returns the value of <code>short</code> type from column by index.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>short</code> type
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    short getShort(int columnIndex);

    /**
     * Returns the value of <code>int</code> type from column by index.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>int</code> type
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    int getInt(int columnIndex);

    /**
     * Returns the value of <code>long</code> type from column by index.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>long</code> type
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    long getLong(int columnIndex);

    /**
     * Returns the value of <code>float</code> type from column by index.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>float</code> type
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    float getFloat(int columnIndex);

    /**
     * Returns the value of <code>double</code> type from column by index.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>double</code> type from column
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    double getDouble(int columnIndex);

    /**
     * Returns the value of <code>BigDecimal</code> object from column by index.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>BigDecimal</code> object
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    BigDecimal getBigDecimal(int columnIndex);

    /**
     * Returns the value of <code>String</code> object from column by index.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>String</code> object
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    String getString(int columnIndex);

    /**
     * Returns the value of <code>byte</code> array from column by index.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>byte</code> array
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    byte[] getBytes(int columnIndex);

    /**
     * Returns the value of <code>Date</code> object from column by index.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>Date</code> object
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Date getDate(int columnIndex);

    /**
     * Returns the value of <code>LocalDate</code> object from column by index.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>LocalDate</code> object
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    LocalDate getLocalDate(int columnIndex);

    /**
     * Returns the value of <code>Time</code> object from column by index.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>Time</code> object
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Time getTime(int columnIndex);

    /**
     * Returns the value of <code>LocalTime</code> object from column by index.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>LocalTime</code> object
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    LocalTime getLocalTime(int columnIndex);

    /**
     * Returns the value of <code>Timestamp</code> object from column by index.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>Timestamp</code> object
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Timestamp getTimestamp(int columnIndex);

    /**
     * Returns the value of <code>LocalDateTime</code> object from column by index.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>LocalDateTime</code> object
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    LocalDateTime getLocalDateTime(int columnIndex);

    /**
     * Returns the value of <code>Object</code> from column by index.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>Object</code>
     * @throws IndexOutOfBoundsException if the column index is not valid
     */
    Object getObject(int columnIndex);

    /**
     * Returns the value of <code>Boolean</code> object from column by name.
     *
     * @param columnName column name in the result set
     * @return the value of <code>Boolean</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Boolean getBoolean(String columnName);

    /**
     * Returns the value of <code>Byte</code> object from column by name.
     *
     * @param columnName column name in the result set
     * @return the value of <code>Byte</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Byte getByte(String columnName);

    /**
     * Returns the value of <code>Short</code> object from column by name.
     *
     * @param columnName column name in the result set
     * @return the value of <code>Short</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Short getShort(String columnName);

    /**
     * Returns the value of <code>Integer</code> object from column by name.
     *
     * @param columnName column name in the result set
     * @return the value of <code>Integer</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Integer getInt(String columnName);

    /**
     * Returns the value of <code>Long</code> object from column by name.
     *
     * @param columnName column name in the result set
     * @return the value of <code>Long</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Long getLong(String columnName);

    /**
     * Returns the value of <code>Float</code> object from column by name.
     *
     * @param columnName column name in the result set
     * @return the value of <code>Float</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Float getFloat(String columnName);

    /**
     * Returns the value of <code>Double</code> object from column by name.
     *
     * @param columnName column name in the result set
     * @return the value of <code>Double</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Double getDouble(String columnName);

    /**
     * Returns the value of <code>BigDecimal</code> object from column by name.
     *
     * @param columnName column name in the result set
     * @return the value of <code>BigDecimal</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    BigDecimal getBigDecimal(String columnName);

    /**
     * Returns the value of <code>String</code> object from column by name.
     *
     * @param columnName column name in the result set
     * @return the value of <code>String</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    String getString(String columnName);

    /**
     * Returns the value of <code>byte</code> array from column by name.
     *
     * @param columnName column name in the result set
     * @return the value of <code>byte</code> array, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    byte[] getBytes(String columnName);

    /**
     * Returns the value of <code>Date</code> object from column by name.
     *
     * @param columnName column name in the result set
     * @return the value of <code>Date</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Date getDate(String columnName);

    /**
     * Returns the value of <code>LocalDate</code> object from column by name.
     *
     * @param columnName column name in the result set
     * @return the value of <code>LocalDate</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    LocalDate getLocalDate(String columnName);

    /**
     * Returns the value of <code>Time</code> object from column by name.
     *
     * @param columnName column name in the result set
     * @return the value of <code>Time</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Time getTime(String columnName);

    /**
     * Returns the value of <code>LocalTime</code> object from column by name.
     *
     * @param columnName column name in the result set
     * @return the value of <code>LocalTime</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    LocalTime getLocalTime(String columnName);

    /**
     * Returns the value of <code>Timestamp</code> object from column by name.
     *
     * @param columnName column name in the result set
     * @return the value of <code>Timestamp</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Timestamp getTimestamp(String columnName);

    /**
     * Returns the value of <code>LocalDateTime</code> object from column by name.
     *
     * @param columnName column name in the result set
     * @return the value of <code>LocalDateTime</code> object, or
     *         <code>null</code> if the result set does not contain a column with this name
     * @throws ClassCastException if the column is of an inappropriate type
     */
    LocalDateTime getLocalDateTime(String columnName);

    /**
     * Returns the value of <code>Object</code> from column by name.
     *
     * @param columnName column name in the result set
     * @return the value of <code>Object</code>, or
     *         <code>null</code> if the result set does not contain a column with this name
     */
    Object getObject(String columnName);
}
