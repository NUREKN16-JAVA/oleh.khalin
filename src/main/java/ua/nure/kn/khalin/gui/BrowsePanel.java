package main.java.ua.nure.kn.khalin.gui;

import main.java.ua.nure.kn.khalin.User;
import main.java.ua.nure.kn.khalin.db.DatabaseException;
import main.java.ua.nure.kn.khalin.gui.main.MainFrame;
import main.java.ua.nure.kn.khalin.utilities.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Objects;

public class BrowsePanel extends JPanel implements ActionListener {

    private static final String BROWSE_PANEL = "browsePanel";
    private static final String ADD_BUTTON = "addButton";
    private static final String EDIT_BUTTON = "editButton";
    private static final String DELETE_BUTTON = "deleteButton";
    private static final String DETAILS_BUTTON = "detailsButton";
    private static final String USER_TABLE = "userTable";
    private static final String ERROR_TITLE = "Error";

    private MainFrame parent;
    private JScrollPane tablePanel;
    private JPanel buttonPanel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton detailsButton;
    private JTable userTable;

    public BrowsePanel(MainFrame parent) {
        this.parent = parent;
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        this.setName(BROWSE_PANEL);
        add(getTablePanel(), BorderLayout.CENTER);
        add(getButtonsPanel(), BorderLayout.SOUTH);
    }

    private JScrollPane getTablePanel() {
        if (Objects.isNull(tablePanel)) {
            tablePanel = new JScrollPane(getUserTable());
        }
        return tablePanel;
    }

    private JPanel getButtonsPanel() {
        if (Objects.isNull(buttonPanel)) {
            buttonPanel = new JPanel();
            buttonPanel.add(getAddButton());
            buttonPanel.add(getEditButton());
            buttonPanel.add(getDeleteButton());
            buttonPanel.add(getDetailsButton());
        }
        return buttonPanel;
    }

    private JButton getAddButton() {
        if (Objects.isNull(addButton)) {
            addButton = new JButton();
            addButton.setText(Messages.getString("BrowsePanel.addButton"));
            addButton.setName(ADD_BUTTON);
            addButton.setActionCommand("add");
            addButton.addActionListener(this);
        }
        return addButton;
    }

    private JButton getEditButton() {
        if (Objects.isNull(editButton)) {
            editButton = new JButton();
            editButton.setText(Messages.getString("editButton"));
            editButton.setName(EDIT_BUTTON);
            editButton.setActionCommand("edit");
            editButton.addActionListener(this);
        }
        return editButton;
    }

    private JButton getDeleteButton() {
        if (Objects.isNull(deleteButton)) {
            deleteButton = new JButton();
            deleteButton.setText(Messages.getString("deleteButton"));
            deleteButton.setName(DELETE_BUTTON);
            deleteButton.setActionCommand("delete");
            deleteButton.addActionListener(this);
        }
        return deleteButton;
    }

    private JButton getDetailsButton() {
        if (Objects.isNull(detailsButton)) {
            detailsButton = new JButton();
            detailsButton.setText(Messages.getString("detailsButton"));
            detailsButton.setName(DETAILS_BUTTON);
            detailsButton.setActionCommand("details");
            detailsButton.addActionListener(this);
        }
        return detailsButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if ("add".equalsIgnoreCase(actionCommand)) {
            this.setVisible(false);
            parent.showAddPanel();
        }
        if (userTable.getSelectedRow() != -1) {
            if ("details".equalsIgnoreCase(actionCommand)) {
                try {
                    User user = getSelectedUser();
                    this.setVisible(false);
                    parent.showDetailsPanel(user);
                } catch (DatabaseException e1) {
                    JOptionPane.showMessageDialog(this, e1.getMessage(), ERROR_TITLE,
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            if ("edit".equalsIgnoreCase(actionCommand)) {
                try {
                    User user = getSelectedUser();
                    this.setVisible(false);
                    parent.showUpdatePanel(user);
                } catch (DatabaseException e1) {
                    JOptionPane.showMessageDialog(this, e1.getMessage(), ERROR_TITLE,
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            if ("delete".equalsIgnoreCase(actionCommand)) {
                try {
                    User user = getSelectedUser();
                    int answer = JOptionPane.showConfirmDialog(this, Messages.getString("deleteConfirmation") + user + "?");
                    if (answer == 0) {
                        parent.getUserDao().deleteUser(user);
                        this.initTable();
                    }
                } catch (DatabaseException e1) {
                    JOptionPane.showMessageDialog(this, e1.getMessage(), ERROR_TITLE,
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private User getSelectedUser() throws DatabaseException {
        int row = userTable.getSelectedRow();
        long userId = Long.parseLong(String.valueOf(userTable.getValueAt(row, 0)));
        return parent.getUserDao().findUser(userId);
    }

    private JTable getUserTable() {
        if (Objects.isNull(userTable)) {
            userTable = new JTable();
            userTable.setName(USER_TABLE);
        }
        initTable();
        return userTable;
    }

    public void initTable() {
        UserTableModel model;
        try {
            model = new UserTableModel(parent.getUserDao().findAll());
        } catch (DatabaseException e) {
            model = new UserTableModel(Collections.emptyList());
            JOptionPane.showMessageDialog(this, e.getMessage(), ERROR_TITLE,
                    JOptionPane.ERROR_MESSAGE);
        }
        userTable.setModel(model);
    }
}
