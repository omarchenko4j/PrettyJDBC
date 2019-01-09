package org.prettyjdbc.core;

import org.prettyjdbc.core.session.Session;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * The main function of the session factory is creating new {@link Session} instances between database and application.
 * Usually an application has a single {@link SessionFactory} instance
 * but to support different {@link DataSource} it is necessary to create several factories.
 * The internal state of the {@link SessionFactory} is immutable so it is thread safe!
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
     * Unwrapping {@link DataSource} from the current {@link SessionFactory} for use outside.
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
        throw new NotImplementedException();
    }

    /**
     * This method is the entry point for creating a {@link SessionFactory} based on {@link DataSource}.
     *
     * @param supplier data source provider
     * @return a new session factory
     */
    public static SessionFactory create(DataSourceSupplier supplier) {
        return new SessionFactory(supplier.get());
    }

    /**
     * Creates a new {@link Session} without the need for a {@link SessionFactory} instance.
     * The session will be obtained as is and management must occur from outside.
     *
     * @param connection connection to wrap in session
     * @return successfully created session
     */
    public static Session newSession(Connection connection) {
        throw new NotImplementedException();
    }
}
