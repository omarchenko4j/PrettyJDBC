package org.prettyjdbc.core.transaction;

/**
 * This abstraction defines a unit of work with a relational database. The lifecycle of a <code>Transaction</code> begins
 * with a method {@link Transaction#begin()} and ends with methods {@link Transaction#commit()} if successful
 * or {@link Transaction#rollback()} otherwise.
 * <p/>
 * A transaction is associated with a {@link org.prettyjdbc.core.session.Session} and is usually initiated by a call to
 * {@link org.prettyjdbc.core.session.Session#beginTransaction()}. A single session might span multiple transactions
 * since the notion of a session (a conversation between the Java application and the database) is of coarser
 * granularity than the notion of a transaction. However, it is intended that there be at most one uncommitted transaction
 * associated with a particular {@link org.prettyjdbc.core.session.Session} at any time.
 *
 * @author Oleg Marchenko
 *
 * @see org.prettyjdbc.core.session.Session
 */

public interface Transaction {

    /**
     * Begins a new resource transaction within the current session.
     *
     * @throws RuntimeException if a database access error occurs
     *  or this method is called when the session connection is closed
     */
    void begin();

    /**
     * Fixation of all changes within the current active transaction.
     * This method should be used only after the method {@link Transaction#begin()}.
     *
     * @throws RuntimeException if a database access error occurs
     *  or this method is called when the session connection is closed
     */
    void commit();

    /**
     * Cancels all changes made to the current transaction.
     * This method should be used only after the method {@link Transaction#begin()}.
     *
     *@throws RuntimeException if a database access error occurs
     *  or this method is called when the session connection is closed
     */
    void rollback();

    /**
     * Returns the current local status of this transaction. This only accounts for the local view of the transaction status.
     * In other words it does not check the status of the actual underlying transaction.
     *
     * @return the current local status of this transaction
     */
    TransactionStatus getStatus();
}
