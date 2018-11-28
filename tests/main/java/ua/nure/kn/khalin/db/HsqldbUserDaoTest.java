package main.java.ua.nure.kn.khalin.db;

import main.java.ua.nure.kn.khalin.User;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class HsqldbUserDaoTest extends DatabaseTestCase {

    private static final String MY_FIRST_NAME = "John";
    private static final String MY_LAST_NAME  = "Doe";
    private static Date myDate;

    private HsqldbUserDao userDao;
    private User my_user;
    private ConnectionFactory my_connection_factory;

    private Calendar my_calendar;

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        my_connection_factory = new CustomConnectionFactory();
        return new DatabaseConnection(my_connection_factory.getConnection
                (new String(), new String(), new String()));
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new XmlDataSet(getClass().getClassLoader()
                .getResourceAsStream("userdataset.xml"));
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        userDao = new HsqldbUserDao(my_connection_factory);
        my_user = new User();
        my_calendar = Calendar.getInstance();
        myDate = new SimpleDateFormat("dd-MMM-yyyy").parse("29-Jan-1999");
    }
    @Test
    public void testCreate(){
        my_user = new User();
        my_user.setDateOfBirth(myDate);
        my_user.setFirstName(MY_FIRST_NAME);
        my_user.setLastName(MY_LAST_NAME);
        my_user.setId(null);

        assertNull(my_user.getId());

        try {
            my_user = userDao.createUser(my_user);

            assertNotNull(my_user);
            assertNotNull(my_user.getId());

        } catch (DatabaseException e) {
            fail(e.toString());
        }

    }

    @Test
    public void testFindAll(){
        try {
            Collection users = userDao.findAll();

            assertNotNull("Collection is null", users);
            assertEquals("Collection size", 2, users.size());

        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteUser() {
        my_user = new User();
        my_user.setDateOfBirth(myDate);
        my_user.setFirstName(MY_FIRST_NAME);
        my_user.setLastName(MY_LAST_NAME);
        my_user.setId(null);

        assertNull(my_user.getId());

        try {
            my_user = userDao.createUser(my_user);

            boolean isDeleted = userDao.deleteUser(my_user);

            assertTrue("User was not deleted!", isDeleted);

        } catch (DatabaseException e) {
            fail(e.toString());
        }
    }

    @Test
    public void testFindUser() {
        my_user = new User();
        my_user.setDateOfBirth(myDate);
        my_user.setFirstName(MY_FIRST_NAME);
        my_user.setLastName(MY_LAST_NAME);
        my_user.setId(null);

        assertNull(my_user.getId());

        try {
            my_user = userDao.createUser(my_user);

            User foundUser = userDao.findUser(my_user.getId());

            assertNotNull("Found user equals null!", foundUser);

        } catch (DatabaseException e) {
            fail(e.toString());
        }
    }

    @Test
    public void testUpdateUser() {
        my_user = new User();
        my_user.setDateOfBirth(myDate);
        my_user.setFirstName(MY_FIRST_NAME);
        my_user.setLastName(MY_LAST_NAME);
        my_user.setId(null);

        assertNull(my_user.getId());

        try {
            my_user = userDao.createUser(my_user);

            boolean isUpdated = userDao.updateUser(my_user);

            assertTrue("User was not updated!", isUpdated);

        } catch (DatabaseException e) {
            fail(e.toString());
        }
    }
}
