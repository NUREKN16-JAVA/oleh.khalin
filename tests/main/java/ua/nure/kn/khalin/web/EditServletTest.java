package ua.nure.vraniuk.usermanagement.web;

import main.java.ua.nure.kn.khalin.User;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class EditServletTest extends MockServletTestCase {
    private static final String OK_BUTTON = "okButton";
    private static final String CANCEL_BUTTON = "cancelButton";

    private static final Long ID_USER = 1000L;
    private static final String FIRST_NAME_USER = "John";
    private static final String LAST_NAME_USER = "Doe";
    private static final Date DATE_USER = java.sql.Date.valueOf(LocalDate.now(ZoneId.systemDefault()));

    protected void setUp() throws Exception {
        super.setUp();
        createServlet(EditServlet.class);
    }

    public void testEdit() {
        User expectedUser = new User(ID_USER, FIRST_NAME_USER, LAST_NAME_USER, DATE_USER);
        getMockUserDao().expect("updateUser", expectedUser);

        addRequestParameter("id", expectedUser.getId().toString());
        addRequestParameter("firstName", expectedUser.getFirstName());
        addRequestParameter("lastName", expectedUser.getLastName());
        addRequestParameter("date", expectedUser.getDateOfBirth().toString());
        addRequestParameter(OK_BUTTON, "ok");
        doPost();
    }

    public void testEditEmptyDate() {
        User expectedUser = new User(ID_USER, FIRST_NAME_USER, LAST_NAME_USER, DATE_USER);

        addRequestParameter("id", expectedUser.getId().toString());
        addRequestParameter("firstName", expectedUser.getFirstName());
        addRequestParameter("lastName", expectedUser.getLastName());
        addRequestParameter(OK_BUTTON, "ok");

        doPost();

        String errorMessage = (String) getWebMockObjectFactory().getMockSession().getAttribute("error");
        assertNotNull("Could not find message in the error scope", errorMessage);
    }

    public void testEditEmptyFirstName() {
        User expectedUser = new User(ID_USER, FIRST_NAME_USER, LAST_NAME_USER, DATE_USER);

        addRequestParameter("id", expectedUser.getId().toString());
        addRequestParameter("lastName", expectedUser.getLastName());
        addRequestParameter("date", expectedUser.getDateOfBirth().toString());
        addRequestParameter(OK_BUTTON, "ok");

        doPost();

        String errorMessage = (String) getWebMockObjectFactory().getMockSession().getAttribute("error");
        assertNotNull("Could not find message in the error scope", errorMessage);
    }

    public void testEditEmptyLastName() {
        User expectedUser = new User(ID_USER, FIRST_NAME_USER, LAST_NAME_USER, DATE_USER);

        addRequestParameter("id", expectedUser.getId().toString());
        addRequestParameter("firstName", expectedUser.getFirstName());
        addRequestParameter("date", DateFormat.getDateInstance().format(new Date()));
        addRequestParameter(OK_BUTTON, "ok");

        doPost();

        String errorMessage = (String) getWebMockObjectFactory().getMockSession().getAttribute("error");
        assertNotNull("Could not find message in the error scope", errorMessage);
    }

    public void testEditIncorrectDate() {
        User expectedUser = new User(ID_USER, FIRST_NAME_USER, LAST_NAME_USER, DATE_USER);

        addRequestParameter("lastName", expectedUser.getLastName());
        addRequestParameter("firstName", expectedUser.getFirstName());
        addRequestParameter("date", "This is the incorrect date!");
        addRequestParameter(OK_BUTTON, "ok");

        doPost();

        String errorMessage = (String) getWebMockObjectFactory().getMockSession().getAttribute("error");
        assertNotNull("Could not find message in the error scope", errorMessage);
    }

}
