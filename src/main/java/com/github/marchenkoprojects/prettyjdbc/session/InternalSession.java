package com.github.marchenkoprojects.prettyjdbc.session;

import com.github.marchenkoprojects.prettyjdbc.query.NamedParameterQuery;
import com.github.marchenkoprojects.prettyjdbc.query.Query;
import com.github.marchenkoprojects.prettyjdbc.query.TypedQuery;
import com.github.marchenkoprojects.prettyjdbc.transaction.InternalTransaction;
import com.github.marchenkoprojects.prettyjdbc.transaction.Transaction;
import com.github.marchenkoprojects.prettyjdbc.transaction.TransactionWork;
import com.github.marchenkoprojects.prettyjdbc.transaction.TransactionWorkWithResult;
import com.github.marchenkoprojects.prettyjdbc.util.NamedParameterQueryProcessor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.github.marchenkoprojects.prettyjdbc.query.Query.closeQuerySoftly;
import static com.github.marchenkoprojects.prettyjdbc.query.Query.isActiveQuery;
import static com.github.marchenkoprojects.prettyjdbc.transaction.InternalTransaction.isActiveTransaction;
import static com.github.marchenkoprojects.prettyjdbc.transaction.InternalTransaction.stopTransactionSoftly;

/**
 * This is the main internal implementation of the {@link Session} interface.
 *
 * @author Oleg Marchenko
 *
 * @see Session
 */
public class InternalSession implements Session {

    private final Connection connection;

    /**
     * Associated transaction with this session.
     */
    private Transaction transaction;
    /**
     * Associated query with this session.
     */
    private Query query;

    public InternalSession(Connection connection) {
        this.connection = connection;
    }

    /**
     * Unwrapping {@link Connection} from the current <code>Session</code> for use outside.
     *
     * @return wrapped JDBC connection
     */
    @Override
    public Connection unwrap() {
        return connection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query createNativeQuery(String sql) {
        if (isActiveQuery(query)) {
            throw new IllegalStateException("Current session already contains the active query");
        }

        PreparedStatement preparedStatement = createStatement(sql);
        Query query = new Query(preparedStatement);
        bindQuery(query);
        return query;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> TypedQuery<T> createNativeQuery(String sql, Class<T> resultType) {
        if (isActiveQuery(query)) {
            throw new IllegalStateException("Current session already contains the active query");
        }

        PreparedStatement preparedStatement = createStatement(sql);
        TypedQuery<T> query = new TypedQuery<>(preparedStatement, resultType);
        bindQuery(query);
        return query;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedParameterQuery createQuery(String sql) {
        if (isActiveQuery(query)) {
            throw new IllegalStateException("Current session already contains the active query");
        }

        NamedParameterQueryProcessor queryProcessor = new NamedParameterQueryProcessor(sql);
        queryProcessor.process();

        PreparedStatement preparedStatement = createStatement(queryProcessor.getNativeQuery());
        NamedParameterQuery query = new NamedParameterQuery(preparedStatement, queryProcessor.getParameters());
        bindQuery(query);
        return query;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> TypedQuery<T> createQuery(String sql, Class<T> resultType) {
        if (isActiveQuery(query)) {
            throw new IllegalStateException("Current session already contains the active query");
        }

        NamedParameterQueryProcessor queryProcessor = new NamedParameterQueryProcessor(sql);
        queryProcessor.process();

        PreparedStatement preparedStatement = createStatement(queryProcessor.getNativeQuery());
        TypedQuery<T> query = new TypedQuery<>(preparedStatement, queryProcessor.getParameters(), resultType);
        bindQuery(query);
        return query;
    }

    private PreparedStatement createStatement(String sql) {
        try {
            return connection.prepareStatement(sql);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void bindQuery(Query query) {
        this.query = query;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Transaction newTransaction() {
        if (isActiveTransaction(transaction)) stopTransaction();

        Transaction transaction = createTransaction();
        bindTransaction(transaction);
        return transaction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Transaction beginTransaction() {
        if (isActiveTransaction(transaction)) return transaction;

        Transaction transaction = createTransaction();
        transaction.begin();
        bindTransaction(transaction);
        return transaction;
    }

    private Transaction createTransaction() {
        return new InternalTransaction(connection);
    }

    private void bindTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doInTransaction(TransactionWork work) {
        Transaction transaction = beginTransaction();
        try {
            work.execute(this);
            transaction.commit();
        }
        catch (Exception e) {
            transaction.rollback();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <R> R doInTransaction(TransactionWorkWithResult<R> work) {
        Transaction transaction = beginTransaction();
        try {
            R result = work.execute(this);
            transaction.commit();
            return result;
        }
        catch (Exception e) {
            transaction.rollback();
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOpen() {
        try {
            return !connection.isClosed();
        }
        catch (SQLException e) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        releaseQuery();
        stopTransaction();
        closeInternal();
    }

    private void releaseQuery() {
        closeQuerySoftly(query);
        query = null;
    }

    private void stopTransaction() {
        stopTransactionSoftly(transaction);
        transaction = null;
    }

    private void closeInternal() {
        try {
            connection.close();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
