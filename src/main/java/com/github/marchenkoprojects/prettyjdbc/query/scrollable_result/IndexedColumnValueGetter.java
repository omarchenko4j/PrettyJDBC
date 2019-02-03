package com.github.marchenkoprojects.prettyjdbc.query.scrollable_result;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * This internal interface provides methods for getting the values of the columns by index in the row of the result set.
 *
 * @author Oleg Marchenko
 *
 * @see ReadOnlyScrollableResult
 */
interface IndexedColumnValueGetter {

    /**
     * Returns the value of <code>boolean</code> type from column by index in the current row of this result set.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>boolean</code> type
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    boolean getBoolean(int columnIndex);

    /**
     * Returns the value of <code>byte</code> type from column by index in the current row of this result set.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>byte</code> type
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    byte getByte(int columnIndex);

    /**
     * Returns the value of <code>short</code> type from column by index in the current row of this result set.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>short</code> type
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    short getShort(int columnIndex);

    /**
     * Returns the value of <code>int</code> type from column by index in the current row of this result set.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>int</code> type
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    int getInt(int columnIndex);

    /**
     * Returns the value of <code>long</code> type from column by index in the current row of this result set.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>long</code> type
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    long getLong(int columnIndex);

    /**
     * Returns the value of <code>float</code> type from column by index in the current row of this result set.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>float</code> type
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    float getFloat(int columnIndex);

    /**
     * Returns the value of <code>double</code> type from column by index in the current row of this result set.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>double</code> type from column
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    double getDouble(int columnIndex);

    /**
     * Returns the value of <code>BigDecimal</code> object from column by index in the current row of this result set.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>BigDecimal</code> object
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    BigDecimal getBigDecimal(int columnIndex);

    /**
     * Returns the value of <code>String</code> object from column by index in the current row of this result set.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>String</code> object
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    String getString(int columnIndex);

    /**
     * Returns the value of <code>byte</code> array from column by index in the current row of this result set.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>byte</code> array
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    byte[] getBytes(int columnIndex);

    /**
     * Returns the value of <code>Date</code> object from column by index in the current row of this result set.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>Date</code> object
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Date getDate(int columnIndex);

    /**
     * Returns the value of <code>LocalDate</code> object from column by index in the current row of this result set.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>LocalDate</code> object
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    LocalDate getLocalDate(int columnIndex);

    /**
     * Returns the value of <code>Time</code> object from column by index in the current row of this result set.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>Time</code> object
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Time getTime(int columnIndex);

    /**
     * Returns the value of <code>LocalTime</code> object from column by index in the current row of this result set.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>LocalTime</code> object
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    LocalTime getLocalTime(int columnIndex);

    /**
     * Returns the value of <code>Timestamp</code> object from column by index in the current row of this result set.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>Timestamp</code> object
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    Timestamp getTimestamp(int columnIndex);

    /**
     * Returns the value of <code>LocalDateTime</code> object from column by index in the current row of this result set.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>LocalDateTime</code> object
     * @throws IndexOutOfBoundsException if the column index is not valid
     * @throws ClassCastException if the column is of an inappropriate type
     */
    LocalDateTime getLocalDateTime(int columnIndex);

    /**
     * Returns the value of <code>Object</code> from column by index in the current row of this result set.
     *
     * @param columnIndex column index in the result set
     * @return the value of <code>Object</code>
     * @throws IndexOutOfBoundsException if the column index is not valid
     */
    Object getObject(int columnIndex);
}
