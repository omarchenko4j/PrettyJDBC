package org.prettyjdbc.core;

/**
 * Represents a data type wrapper for unwrapping the original object.
 *
 * @param <T> type of original object
 *
 * @author Oleg Marchenko
 */

public interface Unwrapable<T> {

    /**
     * Unwrapping the original object for further use.
     *
     * @return the original object
     */
    T unwrap();
}
