package org.prettyjdbc.core;

import org.junit.Assert;
import org.junit.Test;
import org.prettyjdbc.core.transaction.InternalTransaction;
import org.prettyjdbc.core.transaction.Transaction;
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
}
