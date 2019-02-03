package com.github.marchenkoprojects.prettyjdbc.query;

import java.sql.PreparedStatement;

/**
 * This class represents a simple SQL query with basic functionality for performing single operations in a relational database.
 *
 * @author Oleg Marchenko
 *
 * @see AbstractQuery
 */
public class SimpleQuery extends AbstractQuery<SimpleQuery> {

    public SimpleQuery(PreparedStatement preparedStatement) {
        super(preparedStatement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected SimpleQuery getInstance() {
        return this;
    }
}
