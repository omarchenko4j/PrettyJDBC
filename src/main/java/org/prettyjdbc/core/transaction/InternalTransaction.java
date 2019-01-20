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
        if (status == TransactionStatus.NOT_ACTIVE) {
            doBegin();
        }
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
        if (status == TransactionStatus.ACTIVE) {
            doCommit();
            doComplete();
        }
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
        if (status == TransactionStatus.ACTIVE) {
            doRollback();
            doComplete();
        }
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isReadOnly() {
        try {
            return connection.isReadOnly();
        }
        catch (SQLException e) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setReadOnly(boolean readOnly) {
        if (status == TransactionStatus.ACTIVE) {
            throw new IllegalStateException("Read-only mode cannot be set for a transaction in status active");
        }
        try {
            connection.setReadOnly(readOnly);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransactionIsolationLevel getIsolationLevel() {
        try {
            return TransactionIsolationLevel.valueOf(connection.getTransactionIsolation());
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIsolationLevel(TransactionIsolationLevel isolationLevel) {
        if (status == TransactionStatus.ACTIVE) {
            throw new IllegalStateException("Isolation level cannot be set for a transaction in status active");
        }
        try {
            connection.setTransactionIsolation(isolationLevel.getIsolationLevel());
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Allows to immediately stop an active transaction, protecting against possible exceptions.
     *
     * @param transaction transaction to stop
     */
    public static void stopTransactionSoftly(Transaction transaction) {
        if (isActiveTransaction(transaction)) {
            try {
                transaction.rollback();
            }
            catch (Exception e) {
                // Intentionally swallow the exception.
                // TODO: Add the warning after adding logging library.
            }
        }
    }

    /**
     * Returns <code>true</code> if the transaction is still active.
     *
     * @param transaction transaction for check status
     * @return <code>true</code> if this <code>Transaction</code> object is still active;
     *         <code>false</code> otherwise
     */
    public static boolean isActiveTransaction(Transaction transaction) {
        return transaction != null && transaction.getStatus() == TransactionStatus.ACTIVE;
    }
}
