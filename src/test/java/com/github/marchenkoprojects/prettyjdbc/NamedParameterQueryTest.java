package com.github.marchenkoprojects.prettyjdbc;

import com.github.marchenkoprojects.prettyjdbc.query.scrollable_result.ReadOnlyScrollableResult;
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
            ReadOnlyScrollableResult scrollableResult = session
                    .createNamedParameterQuery("SELECT id, original_name, year FROM films WHERE id = :filmId")
                    .setParameter("filmId", 1)
                    .execute();
            Assert.assertNotNull(scrollableResult);
            Assert.assertTrue(scrollableResult.next());
            Assert.assertEquals((int) scrollableResult.getInt("id"), 1);
            Assert.assertEquals(scrollableResult.getString("original_name"), "The Lord of the Rings: The Fellowship of the Ring");
            Assert.assertEquals((int) scrollableResult.getInt("year"), 2001);
        }
    }

    @AfterClass
    public static void afterTests() {
        DatabaseInitializer.destroyDatabase();
    }
}
