package main.java.ua.nure.kn.khalin.gui;

import main.java.ua.nure.kn.khalin.User;
import main.java.ua.nure.kn.khalin.gui.main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class DetailsPanel extends JPanel implements ActionListener {

    private static final String DETAILS_PANEL = "detailsPanel";
    private static final int ROWS = 2;
    private static final int COLS = 2;
    private static final String FULL_NAME_FIELD = "fullNameField";
    private static final String AGE_FIELD = "ageField";
    private static final String CLOSE_BUTTON = "closeButton";

    private MainFrame parent;
    private JButton closeButton;
    private JPanel fieldPanel;
    private JTextField fullNameField;
    private JTextField ageField;

    public DetailsPanel(MainFrame parent) {
        this.parent = parent;
        initialize();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        parent.showBrowsePanel();
    }

    public void showUserDetails(User user) {
        fullNameField.setText(user.getFullName());
        ageField.setText(String.valueOf(user.getAge()));
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setName(DETAILS_PANEL);
        add(getFieldPanel(), BorderLayout.NORTH);
        add(getCloseButton(), BorderLayout.SOUTH);
    }

    private JButton getCloseButton() {
        if (Objects.isNull(closeButton)) {
            closeButton = new JButton();
            closeButton.setText("Закрыть");
            closeButton.setName(CLOSE_BUTTON);
            closeButton.addActionListener(this);
        }
        return closeButton;
    }

    private JPanel getFieldPanel() {
        if (Objects.isNull(fieldPanel)) {
            fieldPanel = new JPanel();
            fieldPanel.setLayout(new GridLayout(ROWS, COLS));
            addLabeledImmutableField(fieldPanel, "Полное имя", getFullNameField());
            addLabeledImmutableField(fieldPanel, "Возраст", getAgeField());
        }
        return fieldPanel;
    }

    private void addLabeledImmutableField(JPanel panel, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        label.setLabelFor(textField);
        panel.add(label);
        panel.add(textField);
    }

    private JTextField getFullNameField() {
        if (Objects.isNull(fullNameField)) {
            fullNameField = new JTextField();
            fullNameField.setEditable(false);
            fullNameField.setName(FULL_NAME_FIELD);
        }
        return fullNameField;
    }

    private JTextField getAgeField() {
        if (Objects.isNull(ageField)) {
            ageField = new JTextField();
            ageField.setEditable(false);
            ageField.setName(AGE_FIELD);
        }
        return ageField;
    }
}
