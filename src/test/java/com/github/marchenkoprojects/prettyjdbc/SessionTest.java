package com.github.marchenkoprojects.prettyjdbc;

import com.github.marchenkoprojects.prettyjdbc.model.Film;
import com.github.marchenkoprojects.prettyjdbc.query.NamedParameterQuery;
import com.github.marchenkoprojects.prettyjdbc.query.Query;
import com.github.marchenkoprojects.prettyjdbc.query.TypedQuery;
import com.github.marchenkoprojects.prettyjdbc.session.InternalSession;
import com.github.marchenkoprojects.prettyjdbc.session.Session;
import com.github.marchenkoprojects.prettyjdbc.transaction.Transaction;
import com.github.marchenkoprojects.prettyjdbc.transaction.TransactionStatus;
import com.github.marchenkoprojects.prettyjdbc.util.DatabaseInitializer;
import com.github.marchenkoprojects.prettyjdbc.util.JDBCUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Oleg Marchenko
 */
public class SessionTest {

    @BeforeClass
    public static void beforeTests() {
        DatabaseInitializer.createDatabase();
    }

    @Test
    public void testCreateNativeQuery() {
        Connection connection = JDBCUtils.getConnection();
        try(Session session = SessionFactory.newSession(connection)) {
            Query query = session.createNativeQuery("SELECT * FROM films WHERE id = ?");
            Assert.assertNotNull(query);
            Assert.assertTrue(query.isActive());
        }
    }

    @Test(expected = RuntimeException.class)
    public void testCreateNativeQueryWithSyntaxErrors() {
        Connection connection = JDBCUtils.getConnection();
        try(Session session = SessionFactory.newSession(connection)) {
            session.createNativeQuery("SELECT * FROM films\" WHERE id = ?");
        }
    }

    @Test
    public void testCreateNativeQueryWithTypedResultRetrieval() {
        Connection connection = JDBCUtils.getConnection();
        try(Session session = SessionFactory.newSession(connection)) {
            TypedQuery<Film> query = session
                    .createNativeQuery("SELECT id, original_name, year FROM films WHERE id = ?", Film.class);
            Assert.assertNotNull(query);
            Assert.assertTrue(query.isActive());
        }
    }

    @Test
    public void testCreateQueryWithNamedParameters() {
        Connection connection = JDBCUtils.getConnection();
        try(Session session = SessionFactory.newSession(connection)) {
            NamedParameterQuery query =
                    session.createQuery("SELECT id, original_name, year FROM films WHERE id = :filmId");
            Assert.assertNotNull(query);
            Assert.assertTrue(query.isActive());
        }
    }

    @Test
    public void testCloseAllQueriesWhenSessionCloses() throws SQLException {
        Connection connection = JDBCUtils.getConnection();

        Session session = new InternalSession(connection);
        Assert.assertTrue(session.isOpen());

        Query query = session
                .createQuery("INSERT INTO films VALUES (?, ?, ?)");
        PreparedStatement queryStatement = query.unwrap();
        Assert.assertTrue(query.isActive());
        Assert.assertFalse(queryStatement.isClosed());

        TypedQuery<Film> typedQuery = session
                .createQuery("SELECT id, original_name, year FROM films WHERE id = ?", Film.class);
        PreparedStatement typedQueryStatement = typedQuery.unwrap();
        Assert.assertTrue(typedQuery.isActive());
        Assert.assertFalse(typedQueryStatement.isClosed());

        NamedParameterQuery namedParameterQuery = session
                .createQuery("SELECT * FROM films OFFSET :offset LIMIT :limit");
        PreparedStatement namedParameterQueryStatement = namedParameterQuery.unwrap();
        Assert.assertTrue(namedParameterQuery.isActive());
        Assert.assertFalse(namedParameterQueryStatement.isClosed());

        session.close();
        Assert.assertFalse(session.isOpen());

        Assert.assertTrue(queryStatement.isClosed());
        Assert.assertTrue(typedQueryStatement.isClosed());
        Assert.assertTrue(namedParameterQueryStatement.isClosed());
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

    @AfterClass
    public static void afterTests() {
        DatabaseInitializer.destroyDatabase();
    }
}
