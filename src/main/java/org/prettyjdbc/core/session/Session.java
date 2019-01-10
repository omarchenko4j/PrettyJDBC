package org.prettyjdbc.core.session;

import org.prettyjdbc.core.Unwrapable;
import org.prettyjdbc.core.query.Query;
import org.prettyjdbc.core.transaction.Transaction;

import java.sql.Connection;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * The main runtime interface which describes the contract between a Java application and database.
 * The <code>Session</code> is a wrapper over the JDBC {@link Connection} and is used to simplify working with it.
 * The main function of the session is to create queries in a relational database.
 * <br>
 * To create a query, use the method {@link Session#createQuery(String)} that accepts a SQL query.
 * The lifecycle of a <code>Session</code> is limited to its creation using the {@link org.prettyjdbc.core.SessionFactory}
 * and the destruction using the method {@link Session#close()}.
 *
 * @author Oleg Marchenko
 *
 * @see org.prettyjdbc.core.transaction.Transaction
 * @see org.prettyjdbc.core.query.Query
 */

public interface Session extends Unwrapable<Connection>, AutoCloseable {

    /**
     * Creates a {@link Query} object for sending SQL statements to the database.
     * This object can then be used to efficiently execute this statement multiple times.
     *
     * @param sqlQuery an SQL query that may contain one or more parameters as <tt>?</tt>
     * @return a new simple <code>Query</code> object
     */
    Query createQuery(String sqlQuery);

    /**
     * Begins a unit of work and return the associated {@link Transaction} object.
     * If a new underlying transaction is required, begin the transaction.
     *
     * @return the associated {@link Transaction} object
     * @throws RuntimeException if a database access error occurs
     *  or this method is called when the session connection is closed
     */
    Transaction beginTransaction();

    /**
     * Returns the {@link Transaction} instance associated with this session.
     *
     * @return the current associated transaction or <code>null</code>,
     *  if the transaction has not been started.
     */
    Transaction getTransaction();

    /**
     * This method allows to <tt>atomically</tt> perform database operations without returning the result.
     *
     * @param consumer database operations provider
     */
    void doInTransaction(Consumer<Session> consumer);

    /**
     * This method allows to <tt>atomically</tt> perform database operations with returning the result.
     *
     * @param <R> type of returning result
     * @param function database operations provider with result
     * @return unit of work result
     * @throws RuntimeException if a database access error occurs
     *  or this method is called when the session connection is closed
     */
    <R> R doInTransaction(Function<Session, R> function);

    /**
     * Returns <code>true</code> if the session connection is still open.
     * The session remains open until the method {@link Session#close()} has been called on it
     * or certain fatal errors have not occurred.
     *
     * @return <code>true</code> if this <code>Session</code> object is still open;
     *         <code>false</code> if it is closed
     */
    boolean isOpen();
}
