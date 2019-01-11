package org.prettyjdbc.core.session;

import org.prettyjdbc.core.query.Query;
import org.prettyjdbc.core.transaction.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This is the main internal implementation of the {@link Session} interface.
 *
 * @author Oleg Marchenko
 *
 * @see org.prettyjdbc.core.session.Session
 * @see org.prettyjdbc.core.transaction.Transaction
 * @see org.prettyjdbc.core.query.Query
 */

public class InternalSession implements Session {

    private final Connection connection;
    private Transaction transaction;

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
    public Query createQuery(String sqlQuery) {
        return new Query(getNewPreparedStatement(sqlQuery));
    }

    private PreparedStatement getNewPreparedStatement(String sqlQuery) {
        try {
            return connection.prepareStatement(sqlQuery);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Transaction beginTransaction() {
        transaction = new InternalTransaction(connection);
        transaction.begin();
        return transaction;
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
     * End the session by releasing the JDBC connection and cleaning up.
     */
    @Override
    public void close() {
        try {
            closeInternal();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        closeInternal();
    }

    private void closeInternal() throws SQLException {
        if (!connection.isClosed()) {
            if (hasActiveTransaction()) {
                transaction.rollback();
            }
            connection.close();
        }
    }

    private boolean hasActiveTransaction() {
        return transaction != null && transaction.getStatus() == TransactionStatus.ACTIVE;
    }
}
