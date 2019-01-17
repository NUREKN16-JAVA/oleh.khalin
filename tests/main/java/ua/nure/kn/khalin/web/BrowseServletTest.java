package main.java.ua.nure.kn.khalin.web;

import ua.nure.vraniuk.usermanagement.User;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BrowseServletTest extends MockServletTestCase{

    private static final String EDIT_BUTTON = "editButton";
    private static final String DETAILS_BUTTON = "detailsButton";
    private static final String DELETE_BUTTON = "deleteButton";

    private static final Long ID_USER = 1000L;
    private static final String FIRST_NAME_USER = "John";
    private static final String LAST_NAME_USER = "Doe";
    private static final Date DATE_USER = java.sql.Date.valueOf(LocalDate.now(ZoneId.systemDefault()));


    protected void setUp() throws Exception {
        super.setUp();
        createServlet(BrowseServlet.class);
    }

    public void testBrowse() {
        User user = new User(ID_USER, FIRST_NAME_USER, LAST_NAME_USER, DATE_USER);
        List list = Collections.singletonList(user);
        getMockUserDao().expectAndReturn("findAll", list);
        doGet();
        Collection collection = (Collection) getWebMockObjectFactory().getMockSession().getAttribute("users");
        assertNotNull("Could not find list of users in session", collection);
        assertEquals(list, collection);
    }

    public void testEdit() {
        User expectedUser = new User(ID_USER, FIRST_NAME_USER, LAST_NAME_USER, DATE_USER);
        getMockUserDao().expectAndReturn("findUser", ID_USER, expectedUser);
        addRequestParameter(EDIT_BUTTON, "Edit");
        addRequestParameter("id", ID_USER.toString());
        doPost();
        User actualUser = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNotNull("Could not find user in session", actualUser);
        assertEquals(expectedUser, actualUser);
    }

}
