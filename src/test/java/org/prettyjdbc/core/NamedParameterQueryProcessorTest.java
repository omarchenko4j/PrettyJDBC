package org.prettyjdbc.core;

import org.junit.Assert;
import org.junit.Test;
import org.prettyjdbc.core.util.NamedParameterQueryProcessor;

import java.util.List;

/**
 * @author Oleg Marchenko
 */

public class NamedParameterQueryProcessorTest {

    @Test
    public void testQueryWithOneParameterInClassicStyle() {
        String query = "SELECT * FROM films WHERE id = :filmId";

        NamedParameterQueryProcessor namedParameterQueryProcessor = new NamedParameterQueryProcessor(query);
        namedParameterQueryProcessor.process();

        String nativeQuery = namedParameterQueryProcessor.getNativeQuery();
        Assert.assertNotNull(nativeQuery);
        Assert.assertEquals(nativeQuery, "SELECT * FROM films WHERE id = ?");

        List<String> parameters = namedParameterQueryProcessor.getParameters();
        Assert.assertNotNull(parameters);
        Assert.assertEquals(parameters.size(), 1);
        Assert.assertEquals(parameters.get(0), "filmId");
    }

    @Test
    public void testQueryWithManyParametersInClassicStyle() {
        String query = "SELECT * FROM films WHERE year >= :year ORDER BY year DESC OFFSET :offset LIMIT :limit";

        NamedParameterQueryProcessor namedParameterQueryProcessor = new NamedParameterQueryProcessor(query);
        namedParameterQueryProcessor.process();

        String nativeQuery = namedParameterQueryProcessor.getNativeQuery();
        Assert.assertNotNull(nativeQuery);
        Assert.assertEquals(nativeQuery, "SELECT * FROM films WHERE year >= ? ORDER BY year DESC OFFSET ? LIMIT ?");

        List<String> parameters = namedParameterQueryProcessor.getParameters();
        Assert.assertNotNull(parameters);
        Assert.assertEquals(parameters.size(), 3);
        Assert.assertEquals(parameters.get(0), "year");
        Assert.assertEquals(parameters.get(1), "offset");
        Assert.assertEquals(parameters.get(2), "limit");
    }

    @Test
    public void testQueryWithOneParameterInAlternativeStyle() {
        String query = "SELECT id, original_name, year FROM films WHERE id = :{id}";

        NamedParameterQueryProcessor namedParameterQueryProcessor = new NamedParameterQueryProcessor(query);
        namedParameterQueryProcessor.process();

        String nativeQuery = namedParameterQueryProcessor.getNativeQuery();
        Assert.assertNotNull(nativeQuery);
        Assert.assertEquals(nativeQuery, "SELECT id, original_name, year FROM films WHERE id = ?");

        List<String> parameters = namedParameterQueryProcessor.getParameters();
        Assert.assertNotNull(parameters);
        Assert.assertEquals(parameters.size(), 1);
        Assert.assertEquals(parameters.get(0), "id");
    }

    @Test
    public void testQueryWithManyParametersInAlternativeStyle() {
        String query = "SELECT * FROM films WHERE year >= :{year} ORDER BY year DESC OFFSET :{offset} LIMIT :{limit}";

        NamedParameterQueryProcessor namedParameterQueryProcessor = new NamedParameterQueryProcessor(query);
        namedParameterQueryProcessor.process();

        String nativeQuery = namedParameterQueryProcessor.getNativeQuery();
        Assert.assertNotNull(nativeQuery);
        Assert.assertEquals(nativeQuery, "SELECT * FROM films WHERE year >= ? ORDER BY year DESC OFFSET ? LIMIT ?");

        List<String> parameters = namedParameterQueryProcessor.getParameters();
        Assert.assertNotNull(parameters);
        Assert.assertEquals(parameters.size(), 3);
        Assert.assertEquals(parameters.get(0), "year");
        Assert.assertEquals(parameters.get(1), "offset");
        Assert.assertEquals(parameters.get(2), "limit");
    }

    @Test
    public void testQueryWithOneParameterAndPostgresCast() {
        String query = "SELECT id, year::INTEGER, world_premiere::DATE FROM films WHERE id = :filmId";

        NamedParameterQueryProcessor namedParameterQueryProcessor = new NamedParameterQueryProcessor(query);
        namedParameterQueryProcessor.process();

        String nativeQuery = namedParameterQueryProcessor.getNativeQuery();
        Assert.assertNotNull(nativeQuery);
        Assert.assertEquals(nativeQuery, "SELECT id, year::INTEGER, world_premiere::DATE FROM films WHERE id = ?");

        List<String> parameters = namedParameterQueryProcessor.getParameters();
        Assert.assertNotNull(parameters);
        Assert.assertEquals(parameters.size(), 1);
        Assert.assertEquals(parameters.get(0), "filmId");
    }
}
