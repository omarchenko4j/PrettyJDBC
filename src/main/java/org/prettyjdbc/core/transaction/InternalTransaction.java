package org.prettyjdbc.core.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This is the main internal implementation of the {@link Transaction} interface.
 *
 * @author Oleg Marchenko
 *
 * @see org.prettyjdbc.core.transaction.Transaction
 */

public class InternalTransaction implements Transaction {

    private final Connection connection;
    private TransactionStatus status;

    public InternalTransaction(Connection connection) {
        this.connection = connection;
        this.status = TransactionStatus.NOT_ACTIVE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void begin() {
        doBegin();
    }

    private void doBegin() {
        setAutoCommit(false);
        status = TransactionStatus.ACTIVE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void commit() {
        doCommit();
        doComplete();
    }

    private void doCommit() {
        try {
            connection.commit();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void rollback() {
        doRollback();
        doComplete();
    }

    private void doRollback() {
        try {
            connection.rollback();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void doComplete() {
        setAutoCommit(true);
        status = TransactionStatus.COMPLETED;
    }

    private void setAutoCommit(boolean autoCommit) {
        try {
            connection.setAutoCommit(autoCommit);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransactionStatus getStatus() {
        return status;
    }
}
