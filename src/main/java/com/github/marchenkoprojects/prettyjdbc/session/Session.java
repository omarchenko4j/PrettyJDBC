package com.github.marchenkoprojects.prettyjdbc.session;

import com.github.marchenkoprojects.prettyjdbc.Unwrapable;
import com.github.marchenkoprojects.prettyjdbc.query.NamedParameterQuery;
import com.github.marchenkoprojects.prettyjdbc.query.Query;
import com.github.marchenkoprojects.prettyjdbc.query.TypedQuery;
import com.github.marchenkoprojects.prettyjdbc.transaction.Transaction;
import com.github.marchenkoprojects.prettyjdbc.transaction.TransactionWork;
import com.github.marchenkoprojects.prettyjdbc.transaction.TransactionWorkWithResult;

import java.sql.Connection;

/**
 * The main runtime interface which describes the contract between a Java application and database.
 * The <code>Session</code> is a wrapper over the JDBC {@link Connection} and is used to simplify working with it.
 * The main function of the session is to create queries in a relational database.
 * <br>
 * To create a query, use the method {@link Session#createQuery(String)} that accepts a SQL query.
 * The lifecycle of a <code>Session</code> is limited to its creation using the
 * {@link com.github.marchenkoprojects.prettyjdbc.SessionFactory} and the destruction using the method {@link Session#close()}.
 *
 * @author Oleg Marchenko
 *
 * @see Query
 * @see NamedParameterQuery
 * @see TypedQuery
 * @see Transaction
 */
public interface Session extends Unwrapable<Connection>, AutoCloseable {

    /**
     * Creates a new native query object to send SQL expressions to the database.
     * Native query represents a parameterized SQL query whose parameters are specified as '?' character.
     * <br>
     * <b>Note:</b> This SQL query can be reused and executed multiple times.
     *
     * @param sql an SQL expression without or with parameters like '?'
     * @return a new native query object
     * @see Query
     */
    Query createNativeQuery(String sql);

    /**
     * Creates a new native query object to send SQL expressions to the database with typed result retrieval.
     * Native query represents a parameterized SQL query whose parameters are specified as '?' character.
     * <br>
     * <b>Note:</b> This SQL query can be reused and executed multiple times.
     *
     * @param sql an SQL expression without or with parameters like '?'
     * @param resultType type of result object
     * @return a new native query object
     * @see TypedQuery
     */
    <T> TypedQuery<T> createNativeQuery(String sql, Class<T> resultType);

    /**
     * Creates a new query object with named parameters to send SQL expressions to the database.
     * Named parameters are defined as patterns: <b>:paramName</b> or <b>:{paramName}</b> where <tt>paramName</tt> is name of parameter.
     * <br>
     * <b>Note:</b> This SQL query can be reused and executed multiple times.
     *
     * @param sql an SQL expression with named parameters
     * @return a new query object
     * @see NamedParameterQuery
     */
    NamedParameterQuery createQuery(String sql);

    /**
     * Creates a new query object with named parameters to send SQL expressions to the database with typed result retrieval.
     * Named parameters are defined as patterns: <b>:paramName</b> or <b>:{paramName}</b> where <tt>paramName</tt> is name of parameter.
     * <br>
     * <b>Note:</b> This SQL query can be reused and executed multiple times.
     *
     * @param sql an SQL expression with named parameters
     * @param resultType type of result object
     * @return a new query object
     * @see TypedQuery
     */
    <T> TypedQuery<T> createQuery(String sql, Class<T> resultType);

    /**
     * Creates a new {@link Transaction} object without starting and associates it with the current <code>Session</code>.
     * If an active transaction already exists then it will be rolled back.
     * After creating a new transaction, it must be started using the method {@link Transaction#begin()}.
     *
     * @return not active and associated {@link Transaction} object
     * @throws RuntimeException if a database access error occurs
     *  or this method is called when the session connection is closed
     * @see Transaction
     */
    Transaction newTransaction();

    /**
     * Creates and starts a new unit of work and return the associated {@link Transaction} object.
     * If an active transaction already exists then it will be returned.
     *
     * @return active and associated {@link Transaction} object
     * @throws RuntimeException if a database access error occurs
     *  or this method is called when the session connection is closed
     * @see Transaction
     */
    Transaction beginTransaction();

    /**
     * Returns the {@link Transaction} object associated with this session.
     *
     * @return the current associated transaction or <code>null</code>,
     *  if the transaction has not been created
     * @see Transaction
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
