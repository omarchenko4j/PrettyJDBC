package org.prettyjdbc.core.transaction;

import static java.sql.Connection.*;

/**
 * Describes the basic levels of transaction isolation.
 * Transaction isolation level with varying degrees provide data integrity when simultaneously processing a plurality of processes.
 *
 * @author Oleg Marchenko
 * @see java.sql.Connection#TRANSACTION_NONE
 * @see java.sql.Connection#TRANSACTION_READ_UNCOMMITTED
 * @see java.sql.Connection#TRANSACTION_READ_COMMITTED
 * @see java.sql.Connection#TRANSACTION_REPEATABLE_READ
 * @see java.sql.Connection#TRANSACTION_SERIALIZABLE
 */

public enum TransactionIsolationLevel {
    /**
     * Transactions are not supported.
     */
    NONE(TRANSACTION_NONE),
    /**
     * This level allows a row changed by one transaction to be read
     * by another transaction before any changes in that row have been
     * committed (a "dirty read").
     */
    READ_UNCOMMITTED(TRANSACTION_READ_UNCOMMITTED),
    /**
     * This level only prohibits a transaction
     * from reading a row with uncommitted changes in it.
     */
    READ_COMMITTED(TRANSACTION_READ_COMMITTED),
    /**
     * This level prohibits a transaction from
     * reading a row with uncommitted changes in it, and it also
     * prohibits the situation where one transaction reads a row,
     * a second transaction alters the row, and the first transaction
     * rereads the row, getting different values the second time (a "non-repeatable read").
     */
    REPEATABLE_READ(TRANSACTION_REPEATABLE_READ),
    /**
     * This level includes the prohibitions in repeatable reads and further prohibits the
     * situation where one transaction reads all rows that satisfy
     * a <code>WHERE</code> condition, a second transaction inserts a row that
     * satisfies that <code>WHERE</code> condition, and the first transaction
     * rereads for the same condition, retrieving the additional "phantom" row in the second read.
     */
    SERIALIZABLE(TRANSACTION_SERIALIZABLE);

    private final int nativeLevel;

    TransactionIsolationLevel(int nativeLevel) {
        this.nativeLevel = nativeLevel;
    }

    /**
     * Returns the current transaction isolation level as a unique number.
     *
     * @return number as current transaction isolation level
     */
    public int nativeLevel() {
        return nativeLevel;
    }

    /**
     * Returns an enumeration as the current transaction isolation level from a unique number.
     *
     * @param isolationLevel number as current transaction isolation level
     * @return an enumeration as the current transaction isolation level
     */
    public static TransactionIsolationLevel valueOf(int isolationLevel) {
        switch (isolationLevel) {
            case TRANSACTION_NONE:
                return NONE;
            case TRANSACTION_READ_UNCOMMITTED:
                return READ_UNCOMMITTED;
            case TRANSACTION_READ_COMMITTED:
                return READ_COMMITTED;
            case TRANSACTION_REPEATABLE_READ:
                return REPEATABLE_READ;
            case TRANSACTION_SERIALIZABLE:
                return SERIALIZABLE;
            default:
                throw new IllegalArgumentException("Unknown transaction isolation level");
        }
    }
}
