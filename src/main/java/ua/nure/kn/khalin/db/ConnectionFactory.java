package main.java.ua.nure.kn.khalin.db;

import java.sql.Connection;

public interface ConnectionFactory {
    public Connection getConnection(String url, String user, String password) throws DatabaseException;
}