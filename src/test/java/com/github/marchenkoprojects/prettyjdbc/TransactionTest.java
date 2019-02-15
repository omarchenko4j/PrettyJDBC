package com.github.marchenkoprojects.prettyjdbc;

import com.github.marchenkoprojects.prettyjdbc.transaction.InternalTransaction;
import com.github.marchenkoprojects.prettyjdbc.transaction.Transaction;
import com.github.marchenkoprojects.prettyjdbc.transaction.TransactionIsolationLevel;
import com.github.marchenkoprojects.prettyjdbc.transaction.TransactionStatus;
import com.github.marchenkoprojects.prettyjdbc.util.JDBCUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Oleg Marchenko
 */
public class TransactionTest {

    @Test
    public void testBeginTransaction() throws SQLException {
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
    public void testCommitTransaction() throws SQLException {
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
    public void testRollbackTransaction() throws SQLException {
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
    public void testChangeReadOnlyInActiveTransaction() {
        Connection connection = Mockito.mock(Connection.class);

        Transaction transaction = new InternalTransaction(connection);
        transaction.begin();

        transaction.setReadOnly(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testChangeIsolationLevelInActiveTransaction() {
        Connection connection = Mockito.mock(Connection.class);

        Transaction transaction = new InternalTransaction(connection);
        transaction.begin();

        transaction.setIsolationLevel(TransactionIsolationLevel.SERIALIZABLE);
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
