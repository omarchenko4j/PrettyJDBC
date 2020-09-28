package com.github.marchenkoprojects.prettyjdbc;

import com.github.marchenkoprojects.prettyjdbc.session.Session;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Oleg Marchenko
 */
public class SessionFactoryCurrentSessionTest {
    private static final ThreadPoolExecutor EXECUTOR = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

    @Test
    public void testDifferentSessionsInThreads() throws SQLException, ExecutionException, InterruptedException {
        AtomicInteger connectionCounter = new AtomicInteger(0);

        DataSource dataSource = Mockito.mock(DataSource.class);
        Mockito.when(dataSource.getConnection()).then(invocationOnMock -> {
            int connectionNumber = connectionCounter.incrementAndGet();

            NamedConnection connection = Mockito.mock(NamedConnection.class);
            Mockito.when(connection.getName()).thenReturn("connection-" + connectionNumber);
            return connection;
        });

        SessionFactory sessionFactory = SessionFactory.create(() -> dataSource);

        Future<Session> firstThreadResult = EXECUTOR.submit(sessionFactory::getSession);
        Future<Session> secondThreadResult = EXECUTOR.submit(sessionFactory::getSession);

        Session firstThreadSession = firstThreadResult.get();
        Session secondThreadSession = secondThreadResult.get();

        NamedConnection firstThreadConnection = (NamedConnection) firstThreadSession.unwrap();
        NamedConnection secondThreadConnection = (NamedConnection) secondThreadSession.unwrap();

        String firstThreadConnectionName = firstThreadConnection.getName();
        String secondThreadConnectionName = secondThreadConnection.getName();

        Assert.assertNotNull(firstThreadConnectionName);
        Assert.assertFalse(firstThreadConnectionName.isEmpty());

        Assert.assertNotNull(secondThreadConnectionName);
        Assert.assertFalse(secondThreadConnectionName.isEmpty());

        Assert.assertNotEquals(firstThreadConnectionName, secondThreadConnectionName);

        Assert.assertEquals(connectionCounter.get(), 2);
    }

    @AfterClass
    public static void afterTests() {
        EXECUTOR.shutdown();
    }

    private interface NamedConnection extends Connection {
        String getName();
    }
}
