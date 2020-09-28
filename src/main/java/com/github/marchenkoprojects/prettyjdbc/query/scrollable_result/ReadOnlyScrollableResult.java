package com.github.marchenkoprojects.prettyjdbc.query.scrollable_result;

/**
 * Interface provides read-only access for cached scrollable result by column index or name.
 *
 * @author Oleg Marchenko
 *
 * @see IndexedColumnValueGetter
 * @see NamedColumnValueGetter
 */
public interface ReadOnlyScrollableResult extends IndexedColumnValueGetter, NamedColumnValueGetter {

    /**
     * Moves the cursor to the previous row and returns <code>true</code> if the cursor after the first row.
     *
     * @return <code>true</code> if the cursor after the first row
     */
    boolean previous();

    /**
     * Moves the cursor to the next row and returns <code>true</code> if the cursor before the last row.
     *
     * @return <code>true</code> if the cursor before the last row
     */
    boolean next();

    /**
     * Returns <code>true</code> if this result set contains no rows.
     *
     * @return <code>true</code> if this result set contains no rows
     */
    boolean isEmpty();

    /**
     * Returns <code>true</code> if the cursor is before the first row.
     *
     * @return <code>true</code> if the cursor is before the first row
     */
    boolean isBeforeFirst();

    /**
     * Returns <code>true</code> if the cursor is after the last row.
     *
     * @return <code>true</code> if the cursor is after the last row
     */
    boolean isAfterLast();

    /**
     * Returns <code>true</code> if the cursor is on the first row.
     *
     * @return <code>true</code> if the cursor is on the first row
     */
    boolean isFirst();

    /**
     * Returns <code>true</code> if the cursor is on the last row.
     *
     * @return <code>true</code> if the cursor is on the last row
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
     * Moves the cursor to the first row and returns <code>true</code> if the cursor is on a valid row.
     *
     * @return <code>true</code> if the cursor is on a valid row
     */
    boolean first();

    /**
     * Moves the cursor to the last row and returns <code>true</code> if the cursor is on a valid row.
     *
     * @return <code>true</code> if the cursor is on a valid row
     */
    boolean last();

    /**
     * Returns the current row number. The first row is number 1 and last row is number {@link #getRowCount()} - 1.
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
}
