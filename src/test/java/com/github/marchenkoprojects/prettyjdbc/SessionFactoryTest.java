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
}
