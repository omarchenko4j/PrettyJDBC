package org.prettyjdbc.core.transaction;

import org.prettyjdbc.core.session.Session;

/**
 * This interface describes a contract for the execution of a separate part of transactional working in a database with returning result.
 *
 * @param <R> type of returning result
 *
 * @author Oleg Marchenko
 *
 * @see org.prettyjdbc.core.session.Session#doInTransaction(TransactionWorkWithResult)
 */

@FunctionalInterface
public interface TransactionWorkWithResult<R> {

    /**
     * Perform discrete transactional work with returning result encapsulated by this instance using the provided {@link Session}.
     *
     * @param session the session in which to do the transactional work
     * @return result of transactional work
     */
    R execute(Session session);
}
