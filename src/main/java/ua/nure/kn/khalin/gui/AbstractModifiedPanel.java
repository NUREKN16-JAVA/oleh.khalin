package main.java.ua.nure.kn.khalin.gui;

import main.java.ua.nure.kn.khalin.User;
import main.java.ua.nure.kn.khalin.gui.main.MainFrame;
import main.java.ua.nure.kn.khalin.utilities.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

public abstract class AbstractModifiedPanel extends JPanel implements ActionListener {

    private static final String DATE_PATTERN = "dd.mm.yyyy";
    protected static final DateFormat format = new SimpleDateFormat(DATE_PATTERN);
    protected static final String ERROR_TITLE = "Error";

    private static final int ROWS = 3;
    private static final int COLS = 2;
    private static final String OK_BUTTON = "okButton";
    private static final String CANCEL_BUTTON = "cancelButton";
    private static final String DATE_OF_BIRTH_FIELD = "dateOfBirthField";
    private static final String FIRST_NAME_FIELD = "firstNameField";
    private static final String LAST_NAME_FIELD = "lastNameField";
    private static final Color backColor = Color.WHITE;
    private static final String OK_COMMAND = "ok";
    private static final String CANCEL_COMMAND = "cancel";

    protected final MainFrame parent;
    private JPanel fieldPanel;
    private JPanel buttonPanel;
    private JButton okButton;
    private JButton cancelButton;
    protected JTextField firstNameField;
    protected JTextField lastNameField;
    protected JTextField dateOfBirthField;
    protected User user;

    public AbstractModifiedPanel(MainFrame parent) {
        this.parent = parent;
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        add(getFieldPanel(), BorderLayout.NORTH);
        add(getButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel getFieldPanel() {
        if (Objects.isNull(fieldPanel)) {
            fieldPanel = new JPanel();
            fieldPanel.setLayout(new GridLayout(ROWS, COLS));
            addLabeledField(fieldPanel, Messages.getString("name"), getFirstNameField());
            addLabeledField(fieldPanel, Messages.getString("lastName"), getLastNameField());
            addLabeledField(fieldPanel, Messages.getString("dateOfBirth"), getDateOfBirthField());
        }
        return fieldPanel;
    }

    private void addLabeledField(JPanel fieldPanel, String name, JTextField textField) {
        JLabel label = new JLabel(name);
        label.setLabelFor(textField);
        fieldPanel.add(label);
        fieldPanel.add(textField);
    }

    private JPanel getButtonPanel() {
        if (Objects.isNull(buttonPanel)) {
            buttonPanel = new JPanel();
            buttonPanel.add(getOkButton());
            buttonPanel.add(getCancelButton());
        }
        return buttonPanel;
    }

    private JButton getOkButton() {
        if (Objects.isNull(okButton)) {
            okButton = new JButton();
            okButton.setText(getConfirmButtonText());
            okButton.setName(OK_BUTTON);
            okButton.setActionCommand(OK_COMMAND);
            okButton.addActionListener(this);
        }
        return okButton;
    }

    protected abstract String getConfirmButtonText();

    private JButton getCancelButton() {
        if (Objects.isNull(cancelButton)) {
            cancelButton = new JButton();
            cancelButton.setText(Messages.getString("cancelButton"));
            cancelButton.setName(CANCEL_BUTTON);
            cancelButton.setActionCommand(CANCEL_COMMAND);
            cancelButton.addActionListener(this);
        }
        return cancelButton;
    }

    protected JTextField getFirstNameField() {
        if (Objects.isNull(firstNameField)) {
            firstNameField = new JTextField();
            firstNameField.setName(FIRST_NAME_FIELD);
        }
        return firstNameField;
    }

    protected JTextField getLastNameField() {
        if (Objects.isNull(lastNameField)) {
            lastNameField = new JTextField();
            lastNameField.setName(LAST_NAME_FIELD);
        }
        return lastNameField;
    }

    protected JTextField getDateOfBirthField() {
        if (Objects.isNull(dateOfBirthField)) {
            dateOfBirthField = new JTextField();
            dateOfBirthField.setName(DATE_OF_BIRTH_FIELD);
        }
        return dateOfBirthField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (OK_COMMAND.equalsIgnoreCase(e.getActionCommand())) {
            performAction();
        }
        clearFields();
        setVisible(false);
        parent.showBrowsePanel();
    }

    protected abstract void performAction();

    private void clearFields() {
        getDateOfBirthField().setText("");
        getDateOfBirthField().setBackground(backColor);

        getFirstNameField().setText("");
        getFirstNameField().setBackground(backColor);

        getLastNameField().setText("");
        getLastNameField().setBackground(backColor);
    }
}
