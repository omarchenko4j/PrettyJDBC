package org.prettyjdbc.core;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.prettyjdbc.core.query.SimpleQuery;
import org.prettyjdbc.core.session.InternalSession;
import org.prettyjdbc.core.session.Session;
import org.prettyjdbc.core.transaction.Transaction;
import org.prettyjdbc.core.transaction.TransactionStatus;
import org.prettyjdbc.core.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Oleg Marchenko
 */

public class SessionTest {

    @BeforeClass
    public static void initDatabase() throws SQLException {
        try(Connection connection = JDBCUtils.getConnection()) {
            try(Statement statement = connection.createStatement()) {
                statement.execute(
                        "CREATE TABLE films(" +
                                "id INTEGER NOT NULL, " +
                                "original_name CHARACTER VARYING(120), " +
                                "year SMALLINT)");
            }
        }
    }

    @AfterClass
    public static void destroyDatabase() throws SQLException {
        try(Connection connection = JDBCUtils.getConnection()) {
            try(Statement statement = connection.createStatement()) {
                statement.execute("DROP TABLE films");
            }
        }
    }

    @Test
    public void testCreateQuery() throws SQLException {
        Session session = SessionFactory.newSession(JDBCUtils.getConnection());

        SimpleQuery query = session.createQuery("SELECT id, original_name, year FROM films WHERE id = ?");
        Assert.assertNotNull(query);
        Assert.assertTrue(query.isActive());

        PreparedStatement preparedStatement = query.unwrap();

        session.close();
        Assert.assertFalse(session.isOpen());
        Assert.assertTrue(preparedStatement.isClosed());
    }

    @Test
    public void testBeginTransaction() {
        Connection connection = JDBCUtils.getConnection();
        Assert.assertTrue(JDBCUtils.getAutoCommit(connection));

        try(Session session = new InternalSession(connection)) {
            Assert.assertTrue(JDBCUtils.getAutoCommit(connection));
            Assert.assertNull(session.getTransaction());

            Transaction transaction = session.beginTransaction();
            Assert.assertNotNull(transaction);
            Assert.assertEquals(transaction.getStatus(), TransactionStatus.ACTIVE);

            Assert.assertFalse(JDBCUtils.getAutoCommit(connection));

            transaction.rollback();
            Assert.assertNotNull(transaction);
            Assert.assertEquals(transaction.getStatus(), TransactionStatus.COMPLETED);

            Assert.assertTrue(JDBCUtils.getAutoCommit(connection));
        }
    }

    @Test
    public void testDoInTransaction() {
        Connection connection = JDBCUtils.getConnection();
        Assert.assertTrue(JDBCUtils.getAutoCommit(connection));

        try(Session session = new InternalSession(connection)) {
            Assert.assertTrue(JDBCUtils.getAutoCommit(connection));
            Assert.assertNull(session.getTransaction());

            session.doInTransaction((s) -> {
                Transaction transaction = s.getTransaction();
                Assert.assertNotNull(transaction);
                Assert.assertEquals(transaction.getStatus(), TransactionStatus.ACTIVE);

                Assert.assertFalse(JDBCUtils.getAutoCommit(connection));
            });

            Transaction transaction = session.getTransaction();
            Assert.assertNotNull(transaction);
            Assert.assertEquals(transaction.getStatus(), TransactionStatus.COMPLETED);

            Assert.assertTrue(JDBCUtils.getAutoCommit(connection));
        }
    }

    @Test
    public void testDoInTransactionWithResult() {
        Connection connection = JDBCUtils.getConnection();
        Assert.assertTrue(JDBCUtils.getAutoCommit(connection));

        try(Session session = new InternalSession(connection)) {
            Assert.assertTrue(JDBCUtils.getAutoCommit(connection));
            Assert.assertNull(session.getTransaction());

            Boolean result = session.doInTransaction((s) -> {
                Transaction transaction = s.getTransaction();
                Assert.assertNotNull(transaction);
                Assert.assertEquals(transaction.getStatus(), TransactionStatus.ACTIVE);

                Assert.assertFalse(JDBCUtils.getAutoCommit(connection));

                JDBCUtils.throwException();

                return Boolean.TRUE;
            });
            Assert.assertNull(result);

            Transaction transaction = session.getTransaction();
            Assert.assertNotNull(transaction);
            Assert.assertEquals(transaction.getStatus(), TransactionStatus.COMPLETED);
        }
    }

    @Test
    public void testOpenCloseStatus() {
        Connection connection = JDBCUtils.getConnection();
        Assert.assertFalse(JDBCUtils.isConnectionClosed(connection));

        Session session = new InternalSession(connection);
        Assert.assertTrue(session.isOpen());

        session.close();
        Assert.assertFalse(session.isOpen());
        Assert.assertTrue(JDBCUtils.isConnectionClosed(connection));
    }

    @Test
    public void testRollbackActiveTransactionWhenSessionCloses() {
        Connection connection = JDBCUtils.getConnection();

        Session session = new InternalSession(connection);
        Transaction transaction = session.beginTransaction();
        Assert.assertNotNull(transaction);
        Assert.assertEquals(transaction.getStatus(), TransactionStatus.ACTIVE);

        session.close();
        Assert.assertFalse(session.isOpen());
        Assert.assertNull(session.getTransaction());
        Assert.assertEquals(transaction.getStatus(), TransactionStatus.COMPLETED);
    }
}
