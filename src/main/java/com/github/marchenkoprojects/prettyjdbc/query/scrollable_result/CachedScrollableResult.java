package com.github.marchenkoprojects.prettyjdbc.query.scrollable_result;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Caches data from the {@link ResultSet} providing the same interaction interface.
 *
 * @author Oleg Marchenko
 *
 * @see ReadOnlyScrollableResult
 */
public class CachedScrollableResult implements ReadOnlyScrollableResult {
    private static final int DEFAULT_ROWS_CAPACITY = 64;
    private static final int BEFORE_FIRST_ROW_INDEX = -1;

    private final List<List<Object>> cachedResults;
    private final Map<String, Integer> columnNameToIndexRegistry;
    private int cursorIndex = BEFORE_FIRST_ROW_INDEX;
    private int rowCount;

    public CachedScrollableResult(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        cachedResults = new ArrayList<>(columnCount);
        columnNameToIndexRegistry = new HashMap<>(columnCount + 1, 1);
        for (int i = 1; i <= columnCount; i++) {
            cachedResults.add(new ArrayList<>(DEFAULT_ROWS_CAPACITY));

            String columnName = metaData.getColumnName(i).toLowerCase();
            columnNameToIndexRegistry.put(columnName, i - 1);
        }

        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                cachedResults.get(i - 1).add(resultSet.getObject(i));
            }
            rowCount++;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean previous() {
        --cursorIndex;
        if (cursorIndex < BEFORE_FIRST_ROW_INDEX) {
            cursorIndex = BEFORE_FIRST_ROW_INDEX;
        }
        return cursorIndex > BEFORE_FIRST_ROW_INDEX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean next() {
        ++cursorIndex;
        if (cursorIndex > rowCount) {
            cursorIndex = rowCount;
        }
        return cursorIndex < rowCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return rowCount == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBeforeFirst() {
        return cursorIndex == BEFORE_FIRST_ROW_INDEX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAfterLast() {
        return cursorIndex == rowCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFirst() {
        return cursorIndex == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLast() {
        return cursorIndex == rowCount - 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void beforeFirst() {
        cursorIndex = BEFORE_FIRST_ROW_INDEX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterLast() {
        cursorIndex = rowCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean first() {
        cursorIndex = 0;
        return cursorIndex < rowCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean last() {
        cursorIndex = rowCount - 1;
        return cursorIndex < rowCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRowIndex() {
        return cursorIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRowCount() {
        return rowCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getBoolean(int columnIndex) {
        return (Boolean) getObject(columnIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte getByte(int columnIndex) {
        return (Byte) getObject(columnIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public short getShort(int columnIndex) {
        return (Short) getObject(columnIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getInt(int columnIndex) {
        return (Integer) getObject(columnIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getLong(int columnIndex) {
        return (Long) getObject(columnIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getFloat(int columnIndex) {
        return (Float) getObject(columnIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDouble(int columnIndex) {
        return (Double) getObject(columnIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getBigDecimal(int columnIndex) {
        return (BigDecimal) getObject(columnIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getString(int columnIndex) {
        return (String) getObject(columnIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] getBytes(final int columnIndex) {
        return (byte[]) getObject(columnIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getDate(int columnIndex) {
        return (Date) getObject(columnIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDate getLocalDate(int columnIndex) {
        Date date = getDate(columnIndex);
        if (date == null) return null;

        return date.toLocalDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Time getTime(int columnIndex) {
        return (Time) getObject(columnIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalTime getLocalTime(int columnIndex) {
        Time time = getTime(columnIndex);
        if (time == null) return null;

        return time.toLocalTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp getTimestamp(int columnIndex) {
        return (Timestamp) getObject(columnIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime getLocalDateTime(int columnIndex) {
        Timestamp timestamp = getTimestamp(columnIndex);
        if (timestamp == null) return null;

        return timestamp.toLocalDateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getObject(int columnIndex) {
        return cachedResults.get(columnIndex - 1).get(cursorIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getBoolean(String columnName) {
        return (Boolean) getObject(columnName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte getByte(String columnName) {
        return (Byte) getObject(columnName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short getShort(String columnName) {
        return (Short) getObject(columnName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getInt(String columnName) {
        return (Integer) getObject(columnName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getLong(String columnName) {
        return (Long) getObject(columnName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Float getFloat(String columnName) {
        return (Float) getObject(columnName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getDouble(String columnName) {
        return (Double) getObject(columnName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getBigDecimal(String columnName) {
        return (BigDecimal) getObject(columnName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getString(String columnName) {
        return (String) getObject(columnName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] getBytes(final String columnName) {
        return (byte[]) getObject(columnName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getDate(String columnName) {
        return (Date) getObject(columnName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDate getLocalDate(String columnName) {
        Date date = getDate(columnName);
        if (date == null) return null;

        return date.toLocalDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Time getTime(String columnName) {
        return (Time) getObject(columnName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalTime getLocalTime(String columnName) {
        Time time = getTime(columnName);
        if (time == null) return null;

        return time.toLocalTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp getTimestamp(String columnName) {
        return (Timestamp) getObject(columnName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime getLocalDateTime(String columnName) {
        Timestamp timestamp = getTimestamp(columnName);
        if (timestamp == null) return null;

        return timestamp.toLocalDateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getObject(String columnName) {
        Integer columnIndex = columnNameToIndexRegistry.get(columnName.toLowerCase());
        if (columnIndex == null) return null;

        return getObject(columnIndex + 1);
    }
}
