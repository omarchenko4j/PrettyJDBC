package org.prettyjdbc.core;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.prettyjdbc.core.transaction.InternalTransaction;
import org.prettyjdbc.core.transaction.Transaction;
import org.prettyjdbc.core.transaction.TransactionIsolationLevel;
import org.prettyjdbc.core.transaction.TransactionStatus;
import org.prettyjdbc.core.util.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Oleg Marchenko
 */

public class TransactionTest {

    @Test
    public void testBegin() throws SQLException {
        try(Connection connection = JDBCUtils.getConnection()) {
            Assert.assertTrue(connection.getAutoCommit());

            Transaction transaction = new InternalTransaction(connection);
            Assert.assertTrue(connection.getAutoCommit());
            Assert.assertEquals(transaction.getStatus(), TransactionStatus.NOT_ACTIVE);

            transaction.begin();
            Assert.assertFalse(connection.getAutoCommit());
            Assert.assertEquals(transaction.getStatus(), TransactionStatus.ACTIVE);
        }
    }

    @Test
    public void testCommit() throws SQLException {
        try(Connection connection = JDBCUtils.getConnection()) {
            Assert.assertTrue(connection.getAutoCommit());

            Transaction transaction = new InternalTransaction(connection);
            transaction.begin();
            Assert.assertFalse(connection.getAutoCommit());
            Assert.assertEquals(transaction.getStatus(), TransactionStatus.ACTIVE);

            transaction.commit();
            Assert.assertTrue(connection.getAutoCommit());
            Assert.assertEquals(transaction.getStatus(), TransactionStatus.COMPLETED);
        }
    }

    @Test
    public void testRollback() throws SQLException {
        try(Connection connection = JDBCUtils.getConnection()) {
            Assert.assertTrue(connection.getAutoCommit());

            Transaction transaction = new InternalTransaction(connection);
            transaction.begin();
            Assert.assertFalse(connection.getAutoCommit());
            Assert.assertEquals(transaction.getStatus(), TransactionStatus.ACTIVE);

            transaction.rollback();
            Assert.assertTrue(connection.getAutoCommit());
            Assert.assertEquals(transaction.getStatus(), TransactionStatus.COMPLETED);
        }
    }

    @Test(expected = IllegalStateException.class)
    public void testCustomizingActiveTransaction() throws SQLException {
        try(Connection connection = JDBCUtils.getConnection()) {
            Transaction transaction = new InternalTransaction(connection);
            transaction.begin();

            transaction.setReadOnly(true);
        }
    }

    @Test
    public void testReadOnlyTransaction() throws SQLException {
        try(Connection connection = JDBCUtils.getConnection()) {
            boolean readOnly = connection.isReadOnly();

            Transaction transaction = new InternalTransaction(connection);
            Assert.assertEquals(transaction.isReadOnly(), connection.isReadOnly());

            transaction.setReadOnly(true);
            Assert.assertTrue(connection.isReadOnly());

            transaction.begin();
            transaction.commit();

            Assert.assertEquals(connection.isReadOnly(), readOnly);
        }
    }

    @Test
    public void testDifferentTransactionIsolationLevels() throws SQLException {
        Connection connection = Mockito.mock(Connection.class);
        Mockito.when(connection.getTransactionIsolation()).thenReturn(Connection.TRANSACTION_READ_COMMITTED);

        Transaction transaction = new InternalTransaction(connection);

        transaction.setIsolationLevel(TransactionIsolationLevel.NONE);
        Mockito.verify(connection).setTransactionIsolation(Connection.TRANSACTION_NONE);

        transaction.setIsolationLevel(TransactionIsolationLevel.READ_UNCOMMITTED);
        Mockito.verify(connection).setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

        transaction.setIsolationLevel(TransactionIsolationLevel.READ_COMMITTED);
        Mockito.verify(connection).setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

        transaction.setIsolationLevel(TransactionIsolationLevel.REPEATABLE_READ);
        Mockito.verify(connection).setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

        transaction.setIsolationLevel(TransactionIsolationLevel.SERIALIZABLE);
        Mockito.verify(connection).setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

        transaction.begin();
        transaction.commit();

        Assert.assertEquals(connection.getTransactionIsolation(), Connection.TRANSACTION_READ_COMMITTED);
    }
}
