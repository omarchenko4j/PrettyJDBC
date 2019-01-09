package org.prettyjdbc.core.session;

import org.prettyjdbc.core.query.Query;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This is the main internal implementation of the {@link Session} interface.
 *
 * @author Oleg Marchenko
 *
 * @see org.prettyjdbc.core.session.Session
 */

public class InternalSession implements Session {

    private final Connection connection;

    public InternalSession(Connection connection) {
        this.connection = connection;
    }

    /**
     * Unwrapping {@link Connection} from the current <code>Session</code> for use outside.
     *
     * @return wrapped JDBC connection
     */
    @Override
    public Connection unwrap() {
        return connection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query createQuery(String sqlQuery) {
        throw new NotImplementedException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOpen() {
        try {
            return !connection.isClosed();
        }
        catch (SQLException e) {
            return false;
        }
    }

    /**
     * End the session by releasing the JDBC connection and cleaning up.
     */
    @Override
    public void close() {
        try {
            closeInternal();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        closeInternal();
    }

    private void closeInternal() throws SQLException {
        if (!connection.isClosed()) {
            connection.close();
        }
    }
}
