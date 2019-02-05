package com.github.marchenkoprojects.prettyjdbc;

import com.github.marchenkoprojects.prettyjdbc.session.Session;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Oleg Marchenko
 */
public class SessionFactoryTest {

    @Test
    public void testCreateSessionFactory() {
        DataSource dataSource = Mockito.mock(DataSource.class);

        SessionFactory sessionFactory = SessionFactory.create(() -> dataSource);
        Assert.assertNotNull(sessionFactory);
        Assert.assertEquals(dataSource, sessionFactory.unwrap());
    }

    @Test(expected = NullPointerException.class)
    public void testCreateSessionFactoryWithNullDataSource() {
        SessionFactory.create(() -> null);
    }

    @Test
    public void testOpenSession() throws SQLException {
        Connection connection = Mockito.mock(Connection.class);
        Mockito.when(connection.isClosed()).thenReturn(false);

        DataSource dataSource = Mockito.mock(DataSource.class);
        Mockito.when(dataSource.getConnection()).thenReturn(connection);

        SessionFactory sessionFactory = SessionFactory.create(() -> dataSource);
        try(Session session = sessionFactory.openSession()) {
            Assert.assertNotNull(session);
            Assert.assertTrue(session.isOpen());
        }
        Mockito.verify(connection).close();
    }

    @Test
    public void testNewSession() throws SQLException {
        Connection connection = Mockito.mock(Connection.class);
        Mockito.when(connection.isClosed()).thenReturn(false);

        try(Session session = SessionFactory.newSession(connection)) {
            Assert.assertNotNull(session);
            Assert.assertTrue(session.isOpen());
        }
        Mockito.verify(connection).close();
    }

    @Test
    public void testBindSession() {
        Session session = Mockito.mock(Session.class);

        SessionFactory.bindSession(session);

        SessionFactory sessionFactory = SessionFactory.create(() -> Mockito.mock(DataSource.class));
        Session currentSession = sessionFactory.getCurrentSession();
        Assert.assertNotNull(currentSession);
        Assert.assertEquals(session, currentSession);
    }

    @Test
    public void testClosingCurrentSessionAfterBindNewSession() {
        Session session = Mockito.mock(Session.class);
        Mockito.when(session.isOpen()).thenReturn(true);

        SessionFactory.bindSession(session);

        Session newSession = Mockito.mock(Session.class);
        SessionFactory.bindSession(newSession);

        Mockito.verify(session).close();
    }

    @Test
    public void testUnbindSession() {
        Session session = Mockito.mock(Session.class);
        Mockito.when(session.isOpen()).thenReturn(true);

        SessionFactory.bindSession(session);
        SessionFactory.unbindSession();

        Mockito.verify(session).close();
    }
}
