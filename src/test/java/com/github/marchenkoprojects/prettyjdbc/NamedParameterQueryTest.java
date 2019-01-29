package com.github.marchenkoprojects.prettyjdbc;

import com.github.marchenkoprojects.prettyjdbc.model.Film;
import com.github.marchenkoprojects.prettyjdbc.session.Session;
import com.github.marchenkoprojects.prettyjdbc.util.DatabaseInitializer;
import com.github.marchenkoprojects.prettyjdbc.util.JDBCUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Oleg Marchenko
 */

public class NamedParameterQueryTest {

    @BeforeClass
    public static void beforeTests() {
        DatabaseInitializer.createAndInitDatabase();
    }

    @Test
    public void testQueryExecution() {
        try(Session session = SessionFactory.newSession(JDBCUtils.getConnection())) {
            Film film = session
                    .createNamedParameterQuery("SELECT id, original_name, year FROM films WHERE id = :filmId", Film.class)
                    .setParameter("filmId", 1)
                    .setResultMapper(resultSet -> {
                        Film newFilm = new Film();
                        newFilm.setId(resultSet.getInt("id"));
                        newFilm.setOriginalName(resultSet.getString("original_name"));
                        newFilm.setYear(resultSet.getShort("year"));
                        return newFilm;
                    })
                    .unique();
            Assert.assertNotNull(film);
            Assert.assertEquals(film.getId(), 1);
            Assert.assertEquals(film.getOriginalName(), "The Lord of the Rings: The Fellowship of the Ring");
            Assert.assertEquals(film.getYear(), 2001);
        }
    }

    @AfterClass
    public static void afterTests() {
        DatabaseInitializer.destroyDatabase();
    }
}
