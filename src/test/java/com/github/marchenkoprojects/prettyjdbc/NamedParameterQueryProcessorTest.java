package com.github.marchenkoprojects.prettyjdbc;

import com.github.marchenkoprojects.prettyjdbc.util.NamedParameterQueryProcessor;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author Oleg Marchenko
 */
public class NamedParameterQueryProcessorTest {

    @Test
    public void testProcessQueryWithoutParameters() {
        String query = "SELECT * FROM films";

        NamedParameterQueryProcessor namedParameterQueryProcessor = new NamedParameterQueryProcessor(query);
        namedParameterQueryProcessor.process();

        String nativeQuery = namedParameterQueryProcessor.getNativeQuery();
        Assert.assertNotNull(nativeQuery);
        Assert.assertEquals(nativeQuery, "SELECT * FROM films");

        List<String> parameters = namedParameterQueryProcessor.getParameters();
        Assert.assertNotNull(parameters);
        Assert.assertTrue(parameters.isEmpty());
    }

    @Test
    public void testProcessNativeQuery() {
        String query = "SELECT id, original_name, year FROM films OFFSET ? LIMIT ?";

        NamedParameterQueryProcessor namedParameterQueryProcessor = new NamedParameterQueryProcessor(query);
        namedParameterQueryProcessor.process();

        String nativeQuery = namedParameterQueryProcessor.getNativeQuery();
        Assert.assertNotNull(nativeQuery);
        Assert.assertEquals(nativeQuery, "SELECT id, original_name, year FROM films OFFSET ? LIMIT ?");

        List<String> parameters = namedParameterQueryProcessor.getParameters();
        Assert.assertNotNull(parameters);
        Assert.assertTrue(parameters.isEmpty());
    }

    @Test
    public void testProcessQueryWithOneParameterInClassicStyle() {
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
    public void testProcessQueryWithManyParametersInClassicStyle() {
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
    public void testProcessQueryWithOneParameterInAlternativeStyle() {
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
    public void testProcessQueryWithManyParametersInAlternativeStyle() {
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

    @Test(expected = IllegalStateException.class)
    public void testProcessQueryWithParametersInAlternativeStyleAndWithoutFirstBracket() {
        String query = "SELECT * FROM films WHERE year >= :year}";

        NamedParameterQueryProcessor namedParameterQueryProcessor = new NamedParameterQueryProcessor(query);
        namedParameterQueryProcessor.process();
    }

    @Test(expected = IllegalStateException.class)
    public void testProcessQueryWithParametersInAlternativeStyleAndWithoutLastBracket() {
        String query = "SELECT * FROM films WHERE year >= :{year";

        NamedParameterQueryProcessor namedParameterQueryProcessor = new NamedParameterQueryProcessor(query);
        namedParameterQueryProcessor.process();
    }

    @Test(expected = IllegalStateException.class)
    public void testProcessQueryWithParametersInAdditionalStyleAndDoubleBrackets() {
        String query = "SELECT * FROM films WHERE year >= :{{year}}";

        NamedParameterQueryProcessor namedParameterQueryProcessor = new NamedParameterQueryProcessor(query);
        namedParameterQueryProcessor.process();
    }

    @Test
    public void testProcessQueryWithPostgresCast() {
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
