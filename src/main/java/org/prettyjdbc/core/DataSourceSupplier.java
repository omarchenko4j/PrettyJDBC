package org.prettyjdbc.core;

import javax.sql.DataSource;
import java.util.function.Supplier;

/**
 * This functional interface represents the {@link DataSource} provider.
 *
 * @author Oleg Marchenko
 *
 * @see javax.sql.DataSource
 */

@FunctionalInterface
public interface DataSourceSupplier extends Supplier<DataSource> {
}
