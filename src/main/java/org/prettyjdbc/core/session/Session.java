package org.prettyjdbc.core.session;

import org.prettyjdbc.core.Unwrapable;
import org.prettyjdbc.core.query.NamedParameterQuery;
import org.prettyjdbc.core.query.SimpleQuery;
import org.prettyjdbc.core.query.TypedQuery;
import org.prettyjdbc.core.transaction.Transaction;
import org.prettyjdbc.core.transaction.TransactionWork;
import org.prettyjdbc.core.transaction.TransactionWorkWithResult;

import java.sql.Connection;

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
 * @see org.prettyjdbc.core.query.SimpleQuery
 * @see org.prettyjdbc.core.query.TypedQuery
 * @see org.prettyjdbc.core.query.NamedParameterQuery
 */

public interface Session extends Unwrapable<Connection>, AutoCloseable {

    /**
     * Creates a {@link SimpleQuery} object for sending SQL statements to the database.
     * This object can then be used to efficiently execute this statement multiple times.
     *
     * @param sqlQuery an SQL query
     * @return a new simple <code>Query</code> object
     */
    SimpleQuery createQuery(String sqlQuery);

    /**
     * Creates a {@link TypedQuery} object for sending SQL statements to the database.
     * This object can then be used to efficiently execute this statement multiple times.
     *
     * @param <T> specific type of object to create a typed query
     * @param sqlQuery an SQL query
     * @param type type of result object
     * @return a new <code>TypedQuery</code> object
     */
    <T> TypedQuery<T> createTypedQuery(String sqlQuery, Class<T> type);

    /**
     * Creates a {@link NamedParameterQuery} object with named parameters for sending SQL statements to the database.
     * This object can then be used to efficiently execute this statement multiple times.
     * <br>
     * Named parameters are defined as patterns: ":paramName" or ":{paramName}" where "paramName" is name of parameter.
     *
     * @param <T> specific type of object to create a named parameter query
     * @param sqlQuery an SQL query
     * @param type type of result object
     * @return a new <code>NamedParameterQuery</code> object
     */
    <T> NamedParameterQuery<T> createNamedParameterQuery(String sqlQuery, Class<T> type);

    /**
     * Creates a new {@link Transaction} object without starting and associates it with the current <code>Session</code>.
     * If an active transaction already exists then it will be rolled back.
     * After creating a new transaction, it must be started using the method {@link Transaction#begin()}.
     *
     * @return not active and associated {@link Transaction} object
     * @throws RuntimeException if a database access error occurs
     *  or this method is called when the session connection is closed
     */
    Transaction newTransaction();

    /**
     * Creates and starts a new unit of work and return the associated {@link Transaction} object.
     * If an active transaction already exists then it will be returned.
     *
     * @return active and associated {@link Transaction} object
     * @throws RuntimeException if a database access error occurs
     *  or this method is called when the session connection is closed
     */
    Transaction beginTransaction();

    /**
     * Returns the {@link Transaction} object associated with this session.
     *
     * @return the current associated transaction or <code>null</code>,
     *  if the transaction has not been created
     */
    Transaction getTransaction();

    /**
     * This method allows to <tt>atomically</tt> perform database operations without returning the result.
     *
     * @param work database operations provider
     * @throws RuntimeException if a database access error occurs
     *  or this method is called when the session connection is closed
     */
    void doInTransaction(TransactionWork work);

    /**
     * This method allows to <tt>atomically</tt> perform database operations with returning the result.
     *
     * @param <R> type of returning result
     * @param work database operations with result provider
     * @return result of completed database operations
     * @throws RuntimeException if a database access error occurs
     *  or this method is called when the session connection is closed
     */
    <R> R doInTransaction(TransactionWorkWithResult<R> work);

    /**
     * Returns <code>true</code> if the session connection is still open.
     * The session remains open until the method {@link Session#close()} has been called on it
     * or certain fatal errors have not occurred.
     *
     * @return <code>true</code> if this <code>Session</code> object is still open;
     *         <code>false</code> if it is closed
     */
    boolean isOpen();

    /**
     * End the session by releasing the JDBC connection and cleaning up.
     */
    @Override
    void close();
}
