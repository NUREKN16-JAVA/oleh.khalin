package main.java.ua.nure.kn.khalin.db;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection createConnection() throws DatabaseException;
}
