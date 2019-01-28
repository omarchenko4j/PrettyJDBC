package com.github.marchenkoprojects.prettyjdbc.transaction;

import com.github.marchenkoprojects.prettyjdbc.session.Session;

/**
 * This abstraction defines a unit of work with a relational database. The lifecycle of a <code>Transaction</code> begins
 * with a method {@link Transaction#begin()} and ends with methods {@link Transaction#commit()} if successful
 * or {@link Transaction#rollback()} otherwise.
 * <br>
 * A transaction is associated with a {@link Session} and is usually initiated by a call to
 * {@link Session#beginTransaction()}. A single session might span multiple transactions
 * since the notion of a session (a conversation between the Java application and the database) is of coarser
 * granularity than the notion of a transaction. However, it is intended that there be at most one uncommitted transaction
 * associated with a particular {@link Session} at any time.
 *
 * @author Oleg Marchenko
 *
 * @see com.github.marchenkoprojects.prettyjdbc.session.Session
 */

public interface Transaction {

    /**
     * Begins a new resource transaction within the current session.
     * This method is idempotent and when called repeatedly, it begins a transaction only once.
     *
     * @throws RuntimeException if a database access error occurs
     *  or this method is called when the session connection is closed
     */
    void begin();

    /**
     * Fixation of all changes within the current active transaction.
     * This method should be used only after calling the method {@link Transaction#begin()}.
     *
     * @throws RuntimeException if a database access error occurs
     *  or this method is called when the session connection is closed
     */
    void commit();

    /**
     * Cancels all changes made to the current transaction.
     * This method should be used only after calling the method {@link Transaction#begin()}.
     *
     * @throws RuntimeException if a database access error occurs
     *  or this method is called when the session connection is closed
     */
    void rollback();

    /**
     * Returns the current local status of this transaction. This only accounts for the local view of the transaction status.<br>
     * In other words it does not check the status of the actual underlying transaction.
     *
     * @return the current local status of this transaction
     * @see com.github.marchenkoprojects.prettyjdbc.transaction.TransactionStatus
     */
    TransactionStatus getStatus();

    /**
     * Returns a read-only mode of the current <code>Transaction</code>.
     *
     * @return <code>true</code> if this <code>Connection</code> object is read-only;
     *         <code>false</code> otherwise
     */
    boolean isReadOnly();

    /**
     * Puts this transaction in read-only mode as a hint to the driver to enable database optimizations.
     * <br>
     * <b>Note:</b> This method cannot be called during an active transaction.
     *
     * @param readOnly <code>true</code> enables read-only mode;
     *                 <code>false</code> disables it
     * @throws IllegalStateException if read-only mode is set to the active transaction
     */
    void setReadOnly(boolean readOnly);

    /**
     * Returns isolation level for the current <code>Transaction</code>.
     *
     * @return the current transaction isolation level
     * @see com.github.marchenkoprojects.prettyjdbc.transaction.TransactionIsolationLevel
     */
    TransactionIsolationLevel getIsolationLevel();

    /**
     * Changes the isolation level for the current <code>Transaction</code>.
     * <br>
     * <b>Note:</b> This method cannot be called during an active transaction.
     *
     * @param isolationLevel the transaction isolation level
     * @throws IllegalStateException if the isolation level is set to the active transaction
     * @see com.github.marchenkoprojects.prettyjdbc.transaction.TransactionIsolationLevel
     */
    void setIsolationLevel(TransactionIsolationLevel isolationLevel);
}
