package main.java.ua.nure.kn.khalin.db;

public class DatabaseException extends Exception {
    private String errorMessage;

    public DatabaseException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public DatabaseException() {
        super(new String());
        this.errorMessage = new String();
    }
}
