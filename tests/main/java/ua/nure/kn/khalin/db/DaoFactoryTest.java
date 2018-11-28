package main.java.ua.nure.kn.khalin.db;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DaoFactoryTest extends TestCase {
    private DaoFactory daoFactoryTest;

    @Before
    public void setUp() throws Exception {
        daoFactoryTest = DaoFactory.getInstance();
    }

    @Test
    public void testGetUserDao() {
        assertNotNull("factory is null", daoFactoryTest);

        try {
            UserDao testDao = daoFactoryTest.getUserDao();
            assertNotNull("userdao null", testDao);
        } catch (DatabaseException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
}
