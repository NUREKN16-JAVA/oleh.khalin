package main.java.ua.nure.kn.khalin.gui;

import com.mockobjects.dynamic.Mock;
import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import main.java.ua.nure.kn.khalin.User;
import main.java.ua.nure.kn.khalin.db.DaoFactory;
import main.java.ua.nure.kn.khalin.db.MockDaoFactory;
import main.java.ua.nure.kn.khalin.gui.main.MainFrame;
import main.java.ua.nure.kn.khalin.utilities.Messages;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;

public class MainFrameTest extends JFCTestCase {

    private MainFrame mainFrame;
    private Mock userDao;

    private static final String TEST_FIRST_NAME = "Mike";
    private static final String TEST_LAST_NAME = "Mikovich";
    private static final String DATE_PATTERN = "dd.mm.yyyy";
    private static final String DAO_FACTORY_PROPERTY = "dao.factory";
    private static final String ID = Messages.getString("id");
    private static final String FIRST_NAME = Messages.getString("name");
    private static final String LAST_NAME = Messages.getString("lastName");
    private static final String ADD_PANEL = "addPanel";
    private static final String FIRST_NAME_FIELD = "firstNameField";
    private static final String LAST_NAME_FIELD = "lastNameField";
    private static final String DATE_OF_BIRTH_FIELD = "dateOfBirthField";
    private static final String OK_BUTTON = "okButton";
    private static final String CANCEL_BUTTON = "cancelButton";
    private static final String BROWSE_PANEL = "browsePanel";
    private static final String USER_TABLE = "userTable";
    private static final String ADD_BUTTON = "addButton";
    private static final String EDIT_BUTTON = "editButton";
    private static final String DELETE_BUTTON = "deleteButton";
    private static final String DETAILS_BUTTON = "detailsButton";

    @Before
    public void setUp() throws Exception {
        super.setUp();
        try {
            Properties properties = new Properties();
            properties.setProperty(DAO_FACTORY_PROPERTY, MockDaoFactory.class.getName());
            DaoFactory.init(properties);
            userDao = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
            userDao.expectAndReturn("findAll", new ArrayList<User>());
            userDao.expectAndReturn("findAll", new ArrayList<User>());
            setHelper(new JFCTestHelper());
            mainFrame = new MainFrame();
        } catch (HeadlessException e) {
            e.printStackTrace();
        }
        mainFrame.setVisible(true);
    }

    @Test
    public void testBrowseControls() {
        find(JPanel.class, BROWSE_PANEL);
        find(JTable.class, USER_TABLE);
        find(JButton.class, ADD_BUTTON);
        find(JButton.class, EDIT_BUTTON);
        find(JButton.class, DELETE_BUTTON);
        find(JButton.class, DETAILS_BUTTON);

        JTable table = (JTable) find(JTable.class, USER_TABLE);
        assertEquals(3, table.getColumnCount());
        assertEquals(ID, table.getColumnName(0));
        assertEquals(FIRST_NAME, table.getColumnName(1));
        assertEquals(LAST_NAME, table.getColumnName(2));
    }

    @Test
    public void testAddUser() {
        int expectedRowsCountBefore = 0;
        int expectedRowsCountAfter = 1;
        Date date = new Date();
        DateFormat format = new SimpleDateFormat(DATE_PATTERN);
        String testDate = format.format(date);
        User user = new User(TEST_FIRST_NAME, TEST_LAST_NAME, date);
        User expectedUser = new User(1L, TEST_FIRST_NAME, TEST_LAST_NAME, date);

        userDao.expectAndReturn("create", user, expectedUser);
        userDao.expectAndReturn("findAll", Collections.singletonList(expectedUser));

        JTable table = (JTable) find(JTable.class, USER_TABLE);
        assertEquals(expectedRowsCountBefore, table.getRowCount());

        JButton addButton = (JButton) find(JButton.class, ADD_BUTTON);
        getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
        find(JPanel.class, ADD_PANEL);

        JTextField firstNameField = (JTextField) find(JTextField.class, FIRST_NAME_FIELD);
        JTextField lastNameField = (JTextField) find(JTextField.class, LAST_NAME_FIELD);
        JTextField dateOfBirthField = (JTextField) find(JTextField.class, DATE_OF_BIRTH_FIELD);
        JButton okButton = (JButton) find(JButton.class, OK_BUTTON);
        find(JButton.class, CANCEL_BUTTON);

        getHelper().sendString(new StringEventData(this, firstNameField, TEST_FIRST_NAME));
        getHelper().sendString(new StringEventData(this, lastNameField, TEST_LAST_NAME));
        getHelper().sendString(new StringEventData(this, dateOfBirthField, testDate));
        getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

        find(JPanel.class, BROWSE_PANEL);
        table = (JTable) find(JTable.class, USER_TABLE);
        assertEquals(expectedRowsCountAfter, table.getRowCount());
    }


    @After
    public void tearDown() throws Exception {
        super.tearDown();
        userDao.verify();
        mainFrame.setVisible(false);
        TestHelper.cleanUp(this);
    }

    private Component find(Class componentClass, String name) {
        NamedComponentFinder finder = new NamedComponentFinder(componentClass, name);
        finder.setWait(0);
        Component component = finder.find(mainFrame, 0);
        assertNotNull("Could not find component ‘" + name + "’", component);
        return component;
    }
}
