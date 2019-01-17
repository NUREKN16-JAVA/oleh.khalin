package main.java.ua.nure.kn.khalin.web;

import main.java.ua.nure.kn.khalin.User;
import main.java.ua.nure.kn.khalin.db.DaoFactory;
import main.java.ua.nure.kn.khalin.db.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class EditServlet extends HttpServlet {

    public static final String OK_BUTTON = "okButton";
    public static final String CANCEL_BUTTON = "cancelButton";

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Objects.nonNull(req.getParameter(OK_BUTTON))) {
            doOk(req, resp);
        } else if (Objects.nonNull(req.getParameter(CANCEL_BUTTON))) {
            doCancel(req, resp);
        } else {
            showPage(req, resp);
        }
    }

    protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException{
        try {
            req.getRequestDispatcher(BrowseServlet.EDIT_JSP).forward(req, resp);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServletException(e.getMessage());
        }
    }

    private void doOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        try {
            User userToAlter = getUser(req);
            processUser(userToAlter);
            resp.sendRedirect("browse");
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServletException(e);
        } catch (ValidationException e) {
            req.setAttribute("error", e.getMessage());
            showPage(req, resp);
        }
    }

    private void doCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        resp.sendRedirect("browse");
    }

    protected void processUser(User userToProcess) throws DatabaseException {
        DaoFactory.getInstance().getUserDao().updateUser(userToProcess);
    }

    private User getUser(HttpServletRequest req) throws ValidationException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);
        User userToAlter = new User();

        Long id = null;
        id = Long.parseLong(req.getParameter("id"));
        if(Objects.isNull(id))
            throw new ValidationException("Invalid id.");

        String firstName = new String();
        firstName = req.getParameter("firstName");
        if(Objects.isNull(firstName))
            throw new ValidationException("Invalid firstName.");

        String lastName = new String();
        lastName = req.getParameter("lastName");
        if(Objects.isNull(lastName))
            throw new ValidationException("Invalid lastName.");

        Date date = null;
        try {
            String date_test = req.getParameter("date");
            date = new Date(formatter.parse(date_test).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ValidationException("Invalid date.");
        }
        if(Objects.isNull(date))
            throw new ValidationException("Invalid date.");

        userToAlter.setId(id);
        userToAlter.setFirstName(firstName);
        userToAlter.setLastName(lastName);
        userToAlter.setDateOfBirth(date);

        return userToAlter;
    }
}
