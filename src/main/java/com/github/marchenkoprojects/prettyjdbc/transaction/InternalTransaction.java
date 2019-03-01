package com.github.marchenkoprojects.prettyjdbc.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This is the main internal implementation of the {@link Transaction} interface.
 *
 * @author Oleg Marchenko
 *
 * @see Transaction
 */
public class InternalTransaction implements Transaction {

    private final Connection connection;
    private TransactionStatus status;

    private final boolean initialReadOnly;
    private final int initialIsolationLevel;

    public InternalTransaction(Connection connection) {
        this.connection = connection;
        this.status = TransactionStatus.NOT_ACTIVE;
        this.initialReadOnly = getReadOnlyInternal();
        this.initialIsolationLevel = getIsolationLevelInternal();
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
        changeStatus(TransactionStatus.ACTIVE);
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
        changeStatus(TransactionStatus.COMPLETED);
        setReadOnlyInternal(initialReadOnly);
        setIsolationLevelInternal(initialIsolationLevel);
    }

    private void changeStatus(TransactionStatus status) {
        this.status = status;
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
        return getReadOnlyInternal();
    }

    private boolean getReadOnlyInternal() {
        try {
            return connection.isReadOnly();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
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

        setReadOnlyInternal(readOnly);
    }

    private void setReadOnlyInternal(boolean readOnly) {
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
        return TransactionIsolationLevel.valueOf(getIsolationLevelInternal());
    }

    private int getIsolationLevelInternal() {
        try {
            return connection.getTransactionIsolation();
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

        setIsolationLevelInternal(isolationLevel.nativeLevel());
    }

    private void setIsolationLevelInternal(int isolationLevel) {
        try {
            connection.setTransactionIsolation(isolationLevel);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
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

    /**
     * Allows to immediately stop an active transaction, protecting against possible exceptions.
     *
     * @param transaction transaction to stop
     */
    public static void safeStopTransaction(Transaction transaction) {
        if (isActiveTransaction(transaction)) {
            try {
                transaction.rollback();
            }
            catch (Exception e) {
                // Intentionally swallow the exception.
            }
        }
    }
}
