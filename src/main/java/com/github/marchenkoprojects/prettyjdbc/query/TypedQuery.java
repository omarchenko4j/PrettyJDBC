package com.github.marchenkoprojects.prettyjdbc.query;

import com.github.marchenkoprojects.prettyjdbc.mapper.ResultMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a typed SQL query with the ability to mapping the result in a specific object type.
 * Typed queries extend all the capabilities of {@link SimpleQuery} and allow to use {@link ResultMapper}
 * to convert the {@link ResultSet} to a specific object.
 * <br>
 * After the query is executed the result data tuple can be transformed into a single object or a list of objects.
 * To convert the result set to a single object, use the method {@link TypedQuery#unique()};
 * or convert to a list of objects, use the method {@link TypedQuery#list()}.
 *
 * @param <T> the specific object type
 *
 * @author Oleg Marchenko
 *
 * @see AbstractQuery
 * @see ResultMapper
 */
public class TypedQuery<T> extends AbstractQuery<TypedQuery<T>> {

    private final Class<T> resultType;
    private ResultMapper<T> resultMapper;

    public TypedQuery(PreparedStatement preparedStatement, Class<T> resultType) {
        super(preparedStatement);
        this.resultType = resultType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TypedQuery<T> getInstance() {
        return this;
    }

    /**
     * Sets the result mapper for transforming to specific typed object.
     *
     * @param resultMapper the specific result mapper
     * @return instance of this query
     */
    public TypedQuery<T> setResultMapper(ResultMapper<T> resultMapper) {
        this.resultMapper = resultMapper;
        return this;
    }

    /**
     * Retrieves a single tuple from the {@link ResultSet} and transforms it into a specific object.
     *
     * @return a single specific object or <code>null</code>, if the result set is empty
     * @throws IllegalStateException if the result mapper is null
     * @throws RuntimeException if a database access error occurs
     *  or this method is called when the session connection is closed
     */
    public T unique() {
        checkMapperPresent();

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultMapper.map(resultSet);
            }
            return null;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all tuples from the {@link ResultSet} and transforms it into a list of specific objects.
     *
     * @return a list of specific objects
     * @throws IllegalStateException if the result mapper is null
     * @throws RuntimeException if a database access error occurs
     *  or this method is called when the session connection is closed
     */
    public List<T> list() {
        checkMapperPresent();

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            List<T> list = new ArrayList<>(32);
            while (resultSet.next()) {
                list.add(resultMapper.map(resultSet));
            }
            return list;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkMapperPresent() {
        if (resultMapper == null) {
            throw new IllegalStateException("No result mapper for type \"" + resultType.getSimpleName() + "\"");
        }
    }
}
