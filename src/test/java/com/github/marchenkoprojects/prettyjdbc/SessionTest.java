package com.github.marchenkoprojects.prettyjdbc;

import com.github.marchenkoprojects.prettyjdbc.model.Film;
import com.github.marchenkoprojects.prettyjdbc.query.NamedParameterQuery;
import com.github.marchenkoprojects.prettyjdbc.query.SimpleQuery;
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
    public void testCreateQuery() {
        Session session = SessionFactory.newSession(JDBCUtils.getConnection());

        SimpleQuery query = session
                .createQuery("SELECT id, original_name, year FROM films WHERE id = ?");
        Assert.assertNotNull(query);
        Assert.assertTrue(query.isActive());

        session.close();
    }

    @Test
    public void testCreateTypedQuery() {
        Session session = SessionFactory.newSession(JDBCUtils.getConnection());

        TypedQuery<Film> typedQuery = session
                .createTypedQuery("SELECT id, original_name, year FROM films WHERE id = ?", Film.class);
        Assert.assertNotNull(typedQuery);
        Assert.assertTrue(typedQuery.isActive());

        session.close();
    }

    @Test
    public void testCreateNamedParameterQuery() {
        Session session = SessionFactory.newSession(JDBCUtils.getConnection());

        NamedParameterQuery<Film> namedParameterQuery = session
                .createNamedParameterQuery("SELECT id, original_name, year FROM films WHERE id = ?", Film.class);
        Assert.assertNotNull(namedParameterQuery);
        Assert.assertTrue(namedParameterQuery.isActive());

        session.close();
    }

    @Test
    public void testCloseAllQueriesWhenSessionCloses() throws SQLException {
        Connection connection = JDBCUtils.getConnection();

        Session session = new InternalSession(connection);
        Assert.assertTrue(session.isOpen());

        SimpleQuery query = session
                .createQuery("INSERT INTO films VALUES (?, ?, ?)");
        PreparedStatement queryStatement = query.unwrap();
        Assert.assertTrue(query.isActive());
        Assert.assertFalse(queryStatement.isClosed());

        TypedQuery<Film> typedQuery = session
                .createTypedQuery("SELECT id, original_name, year FROM films WHERE id = ?", Film.class);
        PreparedStatement typedQueryStatement = typedQuery.unwrap();
        Assert.assertTrue(typedQuery.isActive());
        Assert.assertFalse(typedQueryStatement.isClosed());

        NamedParameterQuery<Film> namedParameterQuery = session
                .createNamedParameterQuery("SELECT * FROM films OFFSET :offset LIMIT :limit", Film.class);
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
