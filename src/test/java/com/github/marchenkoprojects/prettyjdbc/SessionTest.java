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
import java.util.function.Function;

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
        try(Session session = new InternalSession(connection)) {
            Query query = session.createNativeQuery("SELECT * FROM films WHERE id = ?");
            Assert.assertNotNull(query);
            Assert.assertTrue(query.isActive());
        }
    }

    @Test(expected = RuntimeException.class)
    public void testCreateNativeQueryWithSyntaxErrors() {
        Connection connection = JDBCUtils.getConnection();
        try(Session session = new InternalSession(connection)) {
            session.createNativeQuery("SELECT * FROM films\" WHERE id = ?");
        }
    }

    @Test
    public void testCreateNativeTypedQuery() {
        Connection connection = JDBCUtils.getConnection();
        try(Session session = new InternalSession(connection)) {
            TypedQuery<Film> query = session
                    .createNativeQuery("SELECT id, original_name, year FROM films WHERE id = ?", Film.class);
            Assert.assertNotNull(query);
            Assert.assertTrue(query.isActive());
        }
    }

    @Test
    public void testCreateQueryWithNamedParameters() {
        Connection connection = JDBCUtils.getConnection();
        try(Session session = new InternalSession(connection)) {
            NamedParameterQuery query =
                    session.createQuery("SELECT id, original_name, year FROM films WHERE id = :filmId");
            Assert.assertNotNull(query);
            Assert.assertTrue(query.isActive());
        }
    }

    @Test
    public void testCreateTypedQueryWithNamedParameters() {
        Connection connection = JDBCUtils.getConnection();
        try(Session session = new InternalSession(connection)) {
            TypedQuery<Film> query = session
                    .createQuery("SELECT * FROM films ORDER BY year OFFSET :{offset} LIMIT :{limit}", Film.class);
            Assert.assertNotNull(query);
            Assert.assertTrue(query.isActive());
        }
    }

    @Test
    public void testCreateNewNativeQueryWhenSessionAlreadyContainsActiveQuery() {
        createNewQueryWhenSessionAlreadyContainsActiveQuery(
                session -> session.createNativeQuery("SELECT * FROM films ORDER BY year"),
                session -> session.createNativeQuery("SELECT * FROM films")
        );
    }

    @Test
    public void testCreateNewNativeTypedQueryWhenSessionAlreadyContainsActiveQuery() {
        createNewQueryWhenSessionAlreadyContainsActiveQuery(
                session -> session.createNativeQuery("SELECT * FROM films ORDER BY year", Film.class),
                session -> session.createNativeQuery("SELECT * FROM films", Film.class)
        );
    }

    @Test
    public void testCreateNewQueryWithNamedParametersWhenSessionAlreadyContainsActiveQuery() {
        createNewQueryWhenSessionAlreadyContainsActiveQuery(
                session -> session.createQuery("SELECT * FROM films ORDER BY year LIMIT :limit"),
                session -> session.createQuery("SELECT * FROM films LIMIT :limit")
        );
    }

    @Test
    public void testCreateNewTypedQueryWithNamedParametersWhenSessionAlreadyContainsActiveQuery() {
        createNewQueryWhenSessionAlreadyContainsActiveQuery(
                session -> session.createQuery("SELECT COUNT(*) FROM films WHERE year = :{year}", Long.class),
                session -> session.createQuery("SELECT * FROM films WHERE original_name LIKE 'The Lord of the Rings%'", Film.class)
        );
    }

    private void createNewQueryWhenSessionAlreadyContainsActiveQuery(Function<Session, Query> firstQueryCreator,
                                                                     Function<Session, Query> secondQueryCreator) {
        Connection connection = JDBCUtils.getConnection();
        try(Session session = new InternalSession(connection)) {
            Query firstQuery = firstQueryCreator.apply(session);
            Assert.assertNotNull(firstQuery);
            Assert.assertTrue(firstQuery.isActive());

            Query secondQuery = secondQueryCreator.apply(session);
            Assert.assertNotNull(secondQuery);
            Assert.assertTrue(secondQuery.isActive());

            Assert.assertFalse(firstQuery.isActive());
        }
    }

    @Test
    public void testCloseBoundNativeQueryWhenSessionCloses() throws SQLException {
        Connection connection = JDBCUtils.getConnection();

        Session session = new InternalSession(connection);
        Assert.assertTrue(session.isOpen());

        Query query = session.createNativeQuery("INSERT INTO films VALUES (?, ?, ?)");
        PreparedStatement queryStatement = query.unwrap();
        Assert.assertTrue(query.isActive());
        Assert.assertFalse(queryStatement.isClosed());

        session.close();
        Assert.assertFalse(session.isOpen());

        Assert.assertTrue(queryStatement.isClosed());
    }

    @Test
    public void testCloseBoundNativeTypedQueryWhenSessionCloses() throws SQLException {
        Connection connection = JDBCUtils.getConnection();

        Session session = new InternalSession(connection);
        Assert.assertTrue(session.isOpen());

        TypedQuery<Film> query = session
                .createNativeQuery("SELECT id, original_name, year FROM films WHERE id = ?", Film.class);
        PreparedStatement queryStatement = query.unwrap();
        Assert.assertTrue(query.isActive());
        Assert.assertFalse(queryStatement.isClosed());

        session.close();
        Assert.assertFalse(session.isOpen());

        Assert.assertTrue(queryStatement.isClosed());
    }

    @Test
    public void testCloseBoundQueryWithNamedParametersWhenSessionCloses() throws SQLException {
        Connection connection = JDBCUtils.getConnection();

        Session session = new InternalSession(connection);
        Assert.assertTrue(session.isOpen());

        NamedParameterQuery query = session
                .createQuery("INSERT INTO films(id, original_name, year) VALUES (:id, :originalName, :year)");
        PreparedStatement queryStatement = query.unwrap();
        Assert.assertTrue(query.isActive());
        Assert.assertFalse(queryStatement.isClosed());

        session.close();
        Assert.assertFalse(session.isOpen());

        Assert.assertTrue(queryStatement.isClosed());
    }

    @Test
    public void testCloseBoundTypedQueryWithNamedParametersWhenSessionCloses() throws SQLException {
        Connection connection = JDBCUtils.getConnection();

        Session session = new InternalSession(connection);
        Assert.assertTrue(session.isOpen());

        TypedQuery<Film> query = session
                .createQuery("SELECT id, original_name, year FROM films WHERE id = :filmId", Film.class);
        PreparedStatement queryStatement = query.unwrap();
        Assert.assertTrue(query.isActive());
        Assert.assertFalse(queryStatement.isClosed());

        session.close();
        Assert.assertFalse(session.isOpen());

        Assert.assertTrue(queryStatement.isClosed());
    }

    @Test
    public void testNewTransaction() {
        Connection connection = JDBCUtils.getConnection();
        Assert.assertTrue(JDBCUtils.getAutoCommit(connection));

        try(Session session = new InternalSession(connection)) {
            Assert.assertTrue(JDBCUtils.getAutoCommit(connection));
            Assert.assertNull(session.getTransaction());

            Transaction transaction = session.newTransaction();
            Assert.assertNotNull(transaction);
            Assert.assertEquals(transaction.getStatus(), TransactionStatus.NOT_ACTIVE);
            Assert.assertTrue(JDBCUtils.getAutoCommit(connection));
        }
    }

    @Test
    public void testMultipleCreationNewTransactions() {
        Connection connection = JDBCUtils.getConnection();
        try(Session session = new InternalSession(connection)) {
            Transaction transaction = session.newTransaction();
            Assert.assertNotNull(transaction);
            Assert.assertEquals(transaction.getStatus(), TransactionStatus.NOT_ACTIVE);

            transaction.begin();
            Assert.assertEquals(transaction.getStatus(), TransactionStatus.ACTIVE);


            Transaction transaction2 = session.newTransaction();
            Assert.assertNotNull(transaction2);
            Assert.assertEquals(transaction2.getStatus(), TransactionStatus.NOT_ACTIVE);

            Assert.assertEquals(transaction.getStatus(), TransactionStatus.COMPLETED);

            transaction2.begin();
            Assert.assertEquals(transaction2.getStatus(), TransactionStatus.ACTIVE);

            Transaction transaction3 = session.newTransaction();
            Assert.assertNotNull(transaction3);
            Assert.assertEquals(transaction3.getStatus(), TransactionStatus.NOT_ACTIVE);

            Assert.assertEquals(transaction2.getStatus(), TransactionStatus.COMPLETED);
        }
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
    public void testMultipleTransactionStart() {
        Connection connection = JDBCUtils.getConnection();
        try(Session session = new InternalSession(connection)) {
            Transaction transaction = session.beginTransaction();
            Assert.assertNotNull(transaction);
            Assert.assertEquals(transaction.getStatus(), TransactionStatus.ACTIVE);

            Transaction transaction2 = session.beginTransaction();
            Assert.assertNotNull(transaction2);
            Assert.assertEquals(transaction2.getStatus(), TransactionStatus.ACTIVE);

            Assert.assertEquals(transaction, transaction2);
        }
    }

    @Test
    public void testDoInTransactionSuccessfully() {
        Connection connection = JDBCUtils.getConnection();
        Assert.assertTrue(JDBCUtils.getAutoCommit(connection));

        try(Session session = new InternalSession(connection)) {
            Assert.assertTrue(JDBCUtils.getAutoCommit(connection));
            Assert.assertNull(session.getTransaction());

            session.doInTransaction(currentSession -> {
                Transaction transaction = currentSession.getTransaction();
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

    @Test(expected = RuntimeException.class)
    public void testDoInTransactionThrowExceptionAfterExceptionInside() {
        Connection connection = JDBCUtils.getConnection();
        try(Session session = new InternalSession(connection)) {
            session.doInTransaction(currentSession -> {
                JDBCUtils.throwException();
            });
        }
    }

    @Test
    public void testDoInTransactionWithResultSuccessfully() {
        Connection connection = JDBCUtils.getConnection();
        Assert.assertTrue(JDBCUtils.getAutoCommit(connection));

        try(Session session = new InternalSession(connection)) {
            Assert.assertTrue(JDBCUtils.getAutoCommit(connection));
            Assert.assertNull(session.getTransaction());

            Boolean result = session.doInTransaction(currentSession -> {
                Transaction transaction = currentSession.getTransaction();
                Assert.assertNotNull(transaction);
                Assert.assertEquals(transaction.getStatus(), TransactionStatus.ACTIVE);

                Assert.assertFalse(JDBCUtils.getAutoCommit(connection));

                return Boolean.TRUE;
            });
            Assert.assertTrue(result);

            Transaction transaction = session.getTransaction();
            Assert.assertNotNull(transaction);
            Assert.assertEquals(transaction.getStatus(), TransactionStatus.COMPLETED);
        }
    }

    @Test
    public void testDoInTransactionWithResultReturnNullAfterExceptionInside() {
        Connection connection = JDBCUtils.getConnection();
        try(Session session = new InternalSession(connection)) {
            Boolean result = session.doInTransaction(currentSession -> {
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
