package main.java.ua.nure.kn.khalin.db;

import main.java.ua.nure.kn.khalin.User;
import main.java.ua.nure.kn.khalin.utilities.Messages;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserTableModel extends AbstractTableModel {

    private final String[] COLUMN_NAMES = new String[]{Messages.getString("id"), Messages.getString("name"),
            Messages.getString("lastName")};
    private final Class[] COLUMN_CLASSES = {Long.class, String.class, String.class};

    private List<User> users;

    public UserTableModel(Collection<User> users) {
        this.users = new ArrayList<>(users);
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = users.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return user.getId();
            case 1:
                return user.getFirstName();
            case 2:
                return user.getLastName();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return COLUMN_CLASSES[columnIndex];
    }

    public void addUsers(Collection users) {
        this.users.addAll(users);
    }

    public void clearUsers() {
        this.users.clear();
    }
}