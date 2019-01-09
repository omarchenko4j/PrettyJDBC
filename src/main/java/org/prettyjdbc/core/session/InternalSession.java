package org.prettyjdbc.core.session;

import org.prettyjdbc.core.query.Query;
import org.prettyjdbc.core.transaction.InternalTransaction;
import org.prettyjdbc.core.transaction.Transaction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This is the main internal implementation of the {@link Session} interface.
 *
 * @author Oleg Marchenko
 *
 * @see org.prettyjdbc.core.session.Session
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
        throw new NotImplementedException();
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
    public void doInTransaction(Consumer<Session> consumer) {
        Transaction transaction = beginTransaction();
        try {
            consumer.accept(this);
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
    public <R> R doInTransaction(Function<Session, R> function) {
        Transaction transaction = beginTransaction();
        try {
            R result = function.apply(this);
            transaction.commit();
            return result;
        }
        catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
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
            connection.close();
        }
    }
}
