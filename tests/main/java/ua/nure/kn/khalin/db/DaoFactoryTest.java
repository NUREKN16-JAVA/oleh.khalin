package main.java.ua.nure.kn.khalin.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DaoFactoryTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getUserDao() {
        try {
            DaoFactory daoFactory = DaoFactory.getInstance();
            assertNotNull("DaoFactory instance is null.", daoFactory);

            UserDao userDao = daoFactory.getUserDao();
            assertNotNull("UserDao is null.", userDao);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
}
