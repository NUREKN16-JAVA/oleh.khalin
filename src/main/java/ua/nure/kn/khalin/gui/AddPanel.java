package main.java.ua.nure.kn.khalin.gui;

import main.java.ua.nure.kn.khalin.User;
import main.java.ua.nure.kn.khalin.db.DatabaseException;
import main.java.ua.nure.kn.khalin.gui.main.MainFrame;
import main.java.ua.nure.kn.khalin.utilities.Messages;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

public class AddPanel extends AbstractModifiedPanel {

    private static final String ADD_PANEL = "addPanel";
    private MainFrame parent;

    public AddPanel(MainFrame parent) {
        super(parent);
        this.parent = parent;
        this.setName(ADD_PANEL);
    }

    @Override
    protected void performAction() {
        User user = new User();
        user.setFirstName(getFirstNameField().getText());
        user.setLastName(getLastNameField().getText());
        try {
            user.setDateOfBirth(format.parse(getDateOfBirthField().getText()));
        } catch (ParseException e1) {
            getDateOfBirthField().setBackground(Color.RED);
            return;
        }
        try {
            parent.getUserDao().create(user);
        } catch (DatabaseException e1) {
            JOptionPane.showMessageDialog(this, e1.getMessage(), ERROR_TITLE,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected String getConfirmButtonText() {
        return Messages.getString("addButton");
    }

}
