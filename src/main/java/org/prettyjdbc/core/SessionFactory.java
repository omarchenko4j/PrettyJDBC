package org.prettyjdbc.core;

import org.prettyjdbc.core.session.InternalSession;
import org.prettyjdbc.core.session.Session;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * The main function of the session factory is creating new {@link Session} instances between database and Java application.
 * Usually an application has a single <code>SessionFactory</code> instance
 * but to support different {@link DataSource} it is necessary to create several factories.
 * The internal state of the <code>SessionFactory</code> is immutable so it is thread safe!
 * <p/>
 * To create a session factory, use the method {@link SessionFactory#create(DataSourceSupplier)}
 * which accepts a {@link DataSourceSupplier}.
 * If there is no {@link DataSource}, use the method {@link SessionFactory#newSession(Connection)}
 * which helps in a simple way to create {@link Session} instances.
 *
 * @author Oleg Marchenko
 *
 * @see org.prettyjdbc.core.session.Session
 */

public final class SessionFactory implements Unwrapable<DataSource> {

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
     * Creates a new {@link Session} between application and database.
     * The session will be obtained as is and management must occur from outside.
     * Usually work with this method occurs in conjunction with try-with-resources
     * because the session is {@link AutoCloseable}.
     *
     * @return successfully created session
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
     * This method is the entry point for creating a <code>SessionFactory</code> based on {@link DataSource}.
     *
     * @param supplier data source provider
     * @return a new session factory
     */
    public static SessionFactory create(DataSourceSupplier supplier) {
        return new SessionFactory(supplier.get());
    }

    /**
     * Creates a new {@link Session} without the need for a <code>SessionFactory</code> instance.
     * The session will be obtained as is and management must occur from outside.
     *
     * @param connection connection to wrap in session
     * @return successfully created session
     */
    public static Session newSession(Connection connection) {
        return new InternalSession(connection);
    }
}
