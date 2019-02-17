package com.github.marchenkoprojects.prettyjdbc;

import com.github.marchenkoprojects.prettyjdbc.session.InternalSession;
import com.github.marchenkoprojects.prettyjdbc.session.Session;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * The main function of the session factory is creating new {@link Session} instances between
 * relational database and Java application. Usually an application has a single <code>SessionFactory</code>
 * instance but to support different {@link DataSource} it is necessary to create several factories.
 * The internal state of the <code>SessionFactory</code> is immutable so it is thread safe!
 * <br>
 * To create a session factory, use the method {@link SessionFactory#create(DataSourceSupplier)}
 * which accepts a {@link DataSourceSupplier}.
 * If there is no {@link DataSource}, use the method {@link SessionFactory#newSession(Connection)}
 * which helps in a simple way to create {@link Session} instances.
 *
 * @author Oleg Marchenko
 *
 * @see Session
 */
public final class SessionFactory implements Unwrapable<DataSource> {
    /**
     * Active session within the current thread.
     */
    private static final ThreadLocal<Session> CURRENT_SESSION = new ThreadLocal<>();

    private final DataSource dataSource;

    private SessionFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Unwrapping {@link DataSource} from the current <code>SessionFactory</code> for use outside.
     *
     * @return wrapped data source
     */
    @Override
    public DataSource unwrap() {
        return dataSource;
    }

    /**
     * Creates a new {@link Session} between Java application and relational database.
     * The session will be obtained as is and management must occur from outside.
     * Usually work with this method occurs in conjunction with try-with-resources
     * because the session is {@link AutoCloseable}.
     *
     * @return successfully created session
     * @see Session
     */
    public Session openSession() {
        return newSession(getNewConnection());
    }

    private Connection getNewConnection() {
        try {
            return dataSource.getConnection();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a session within the current thread.
     * If the current session has not yet been created or is no longer active
     * then a new session will be opened ({@link SessionFactory#openSession()}) and bound to the current thread.
     * <br>
     * <b>Note:</b> The current session will be bound with the thread that called this method.
     * <br>
     * <b>Warning:</b> When the thread has completed work, the current session should be closed!
     *
     * @return the current session
     * @see Session
     */
    public Session getCurrentSession() {
        return openOrObtainSession();
    }

    private Session openOrObtainSession() {
        Session session = CURRENT_SESSION.get();
        if (session == null) {
            session = openSession();
            doBindSession(session);
        }
        return session;
    }

    /**
     * Creates a new {@link Session} without the need for a <code>SessionFactory</code> instance.
     * The session will be obtained as is and management must occur from outside.
     *
     * @param connection connection to wrap in session
     * @return successfully created session
     * @see Session
     */
    public static Session newSession(Connection connection) {
        return new InternalSession(connection);
    }

    /**
     * Binds a session to the current thread.
     * If the current thread already has an active session then it will be forcibly terminated.
     * <br>
     * <b>Warning:</b> If the session was bound to the current thread then after use it is necessary to {@link SessionFactory#unbindSession()}.
     *
     * @param session a new session
     */
    public static void bindSession(Session session) {
        forcedTerminateCurrentSession();
        doBindSession(session);
    }

    private static void doBindSession(Session session) {
        CURRENT_SESSION.set(session);
    }

    /**
     * Unbinds a session from the current thread.
     * The session will be unbind from the current thread and forcibly terminated.
     * If the current session was incomplete active transaction then it will be forcibly rolled back.
     */
    public static void unbindSession() {
        forcedTerminateCurrentSession();
        doUnbindSession();
    }

    private static void doUnbindSession() {
        CURRENT_SESSION.remove();
    }

    private static void forcedTerminateCurrentSession() {
        Session currentSession = CURRENT_SESSION.get();
        if (currentSession != null && currentSession.isOpen()) {
            currentSession.close();
        }
    }

    /**
     * This method is the entry point for creating a <code>SessionFactory</code> based on {@link DataSource}.
     *
     * @param supplier data source provider
     * @return a new session factory
     */
    public static SessionFactory create(DataSourceSupplier supplier) {
        DataSource dataSource = supplier.get();
        if (dataSource == null) {
            throw new NullPointerException("Data source is null");
        }
        return new SessionFactory(dataSource);
    }
}
