package com.github.marchenkoprojects.prettyjdbc;

import javax.sql.DataSource;
import java.util.function.Supplier;

/**
 * This functional interface represents the {@link DataSource} provider.
 *
 * @author Oleg Marchenko
 *
 * @see com.github.marchenkoprojects.prettyjdbc.SessionFactory#create(DataSourceSupplier)
 */

@FunctionalInterface
public interface DataSourceSupplier extends Supplier<DataSource> {

    /**
     * Provides a non-null configured data source.
     *
     * @return a configured data source
     */
    @Override
    DataSource get();
}
