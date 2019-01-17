package main.java.ua.nure.kn.khalin.web;

import main.java.ua.nure.kn.khalin.User;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static main.java.ua.nure.kn.khalin.web.EditServlet.OK_BUTTON;

public class AddServletTest extends MockServletTestCase {

    private static final Long ID_USER = 1000L;
    private static final String FIRST_NAME_USER = "John";
    private static final String LAST_NAME_USER = "Doe";
    private static final Date DATE_USER = java.sql.Date.valueOf(LocalDate.now(ZoneId.systemDefault()));

    protected void setUp() throws Exception {
        super.setUp();
        createServlet(AddServlet.class);
    }

    public void testAdd() {
        User expectedUser = new User(ID_USER, FIRST_NAME_USER, LAST_NAME_USER, DATE_USER);
        User userToSend = new User(FIRST_NAME_USER, LAST_NAME_USER, DATE_USER);
        getMockUserDao().expectAndReturn("create", userToSend, expectedUser);

        addRequestParameter("firstName", expectedUser.getFirstName());
        addRequestParameter("lastName", expectedUser.getLastName());
        addRequestParameter("date", expectedUser.getDateOfBirth().toString());
        addRequestParameter(OK_BUTTON, "ok");
        doPost();
    }

    public void testAddEmptyDate() {
        User expectedUser = new User(ID_USER, FIRST_NAME_USER, LAST_NAME_USER, DATE_USER);

        addRequestParameter("firstName", expectedUser.getFirstName());
        addRequestParameter("lastName", expectedUser.getLastName());
        addRequestParameter(OK_BUTTON, "ok");

        doPost();

        String errorMessage = (String) getWebMockObjectFactory().getMockSession().getAttribute("error");
        assertNotNull("Could nt find message in the error scope", errorMessage);
    }

    public void testAddEmptyFirstName() {
        User expectedUser = new User(ID_USER, FIRST_NAME_USER, LAST_NAME_USER, DATE_USER);

        addRequestParameter("lastName", expectedUser.getLastName());
        addRequestParameter("date",  DateFormat.getDateInstance().format(new Date()));
        addRequestParameter(OK_BUTTON, "ok");

        doPost();

        String errorMessage = (String) getWebMockObjectFactory().getMockSession().getAttribute("error");
        assertNotNull("Could not find message in the error scope", errorMessage);
    }

    public void testAddEmptyLastName() {
        User expectedUser = new User(ID_USER, FIRST_NAME_USER, LAST_NAME_USER, DATE_USER);

        addRequestParameter("firstName", expectedUser.getFirstName());
        addRequestParameter("date", expectedUser.getDateOfBirth().toString());
        addRequestParameter(OK_BUTTON, "ok");

        doPost();

        String errorMessage = (String) getWebMockObjectFactory().getMockSession().getAttribute("error");
        assertNotNull("Coul d not find message in the error scope", errorMessage);
    }

    public void testAddIncorrectDate() {
        User expectedUser = new User(ID_USER, FIRST_NAME_USER, LAST_NAME_USER, DATE_USER);

        addRequestParameter("firstName", expectedUser.getFirstName());
        addRequestParameter("date", "Lol,incorrect!");
        addRequestParameter(OK_BUTTON, "ok");

        doPost();

        String errorMessage = (String) getWebMockObjectFactory().getMockSession().getAttribute("error");
        assertNotNull("Could not find message in the error scope", errorMessage);
    }
}
