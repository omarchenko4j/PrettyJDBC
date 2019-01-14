package org.prettyjdbc.core.session;

import org.prettyjdbc.core.query.Query;
import org.prettyjdbc.core.transaction.InternalTransaction;
import org.prettyjdbc.core.transaction.Transaction;
import org.prettyjdbc.core.transaction.TransactionWork;
import org.prettyjdbc.core.transaction.TransactionWorkWithResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.AbstractQueue;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.function.Consumer;

import static org.prettyjdbc.core.transaction.InternalTransaction.isActiveTransaction;

/**
 * This is the main internal implementation of the {@link Session} interface.
 *
 * @author Oleg Marchenko
 *
 * @see org.prettyjdbc.core.session.Session
 * @see org.prettyjdbc.core.transaction.Transaction
 * @see org.prettyjdbc.core.query.Query
 */

public class InternalSession implements Session {
    private static final int MAX_NUMBER_OF_ASSOCIATED_QUERIES = 16;

    private final Connection connection;

    /**
     * Associated transaction with this session.
     */
    private Transaction transaction;
    /**
     * A queue of associated queries with this session.
     */
    private FixedSizeQueue<Query> queries;

    public InternalSession(Connection connection) {
        this.connection = connection;
        this.queries = new FixedSizeQueue<>(MAX_NUMBER_OF_ASSOCIATED_QUERIES);
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
        Query query = new Query(getNewStatement(sqlQuery));
        bindQuery(query);
        return query;
    }

    private PreparedStatement getNewStatement(String sqlQuery) {
        try {
            return connection.prepareStatement(sqlQuery);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void bindQuery(Query query) {
        queries.offer(query, Query::closeQuerySoftly);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Transaction beginTransaction() {
        if (isActiveTransaction(transaction)) return transaction;

        transaction = new InternalTransaction(connection);
        transaction.begin();
        return transaction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doInTransaction(TransactionWork work) {
        Transaction transaction = beginTransaction();
        try {
            work.execute(this);
            transaction.commit();
        }
        catch (Exception e) {
            transaction.rollback();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <R> R doInTransaction(TransactionWorkWithResult<R> work) {
        Transaction transaction = beginTransaction();
        try {
            R result = work.execute(this);
            transaction.commit();
            return result;
        }
        catch (Exception e) {
            transaction.rollback();
            return null;
        }
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
        releaseQueries();
        stopTransaction();
        closeInternal();
    }

    private void releaseQueries() {
        queries.forEach(Query::closeQuerySoftly);
        queries.clear();
        queries = null;
    }

    private void stopTransaction() {
        InternalTransaction.stopTransactionSoftly(transaction);
        transaction = null;
    }

    private void closeInternal() {
        try {
            connection.close();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A non-blocking queue which automatically evicts elements from the head of the queue when attempting to add new elements onto the queue and it is full.
     * This queue orders elements FIFO (first-in-first-out).
     * <br>
     * An fixed size queue must be configured with a maximum size.
     * Each time an element is added to a full queue, the queue automatically removes its head element.
     * This is different from conventional bounded queues, which either block or reject new elements when full.
     *
     * @author Oleg Marchenko
     *
     * @see java.util.Queue
     */
    private static class FixedSizeQueue<E> extends AbstractQueue<E> {

        private final Queue<E> queue;
        private final int maxSize;

        public FixedSizeQueue(int maxSize) {
            this.queue = new ArrayDeque<>(maxSize + 1);
            this.maxSize = maxSize;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<E> iterator() {
            return queue.iterator();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return queue.size();
        }

        /**
         * Inserts the specified element immediately into this queue and
         * if the maximum size is reached then retrieves and removes the head element.
         *
         * @param e the element to add
         * @return <code>true</code> if the element was added to this queue, else
         *         <code>false</code>
         */
        @Override
        public boolean offer(E e) {
            queue.offer(e);
            if (size() >= maxSize) poll();
            return true;
        }

        /**
         * Inserts the specified element immediately into this queue.
         * If the maximum size is reached then retrieves and removes the head element and accept to consumer.
         *
         * @param e the element to add
         * @param consumer a consumer which accepts a remove element
         * @return <code>true</code> if the element was added to this queue, else
         *         <code>false</code>
         */
        public boolean offer(E e, Consumer<E> consumer) {
            queue.offer(e);
            if (size() >= maxSize) consumer.accept(poll());
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E poll() {
            return queue.poll();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E peek() {
            return queue.peek();
        }
    }
}
