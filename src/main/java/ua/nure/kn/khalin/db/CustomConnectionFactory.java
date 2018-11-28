package main.java.ua.nure.kn.khalin.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CustomConnectionFactory implements ConnectionFactory {
    public String driver =
            "org.hsqldb.jdbcDriver";
    public String dburl =
            "jdbc:hsqldb:file:db/usermanagement";
    public String user = "sa";
    public String password = "";

    public CustomConnectionFactory(String url, String user, String password, String driver) throws DatabaseException {
        if (!url.isEmpty()) {
            dburl = url;
        }
        if (!user.isEmpty()) {
            this.user = user;
        }
        if (!password.isEmpty()) {
            this.password = password;
        }
        if (!driver.isEmpty()) {
            this.driver = driver;
        }
    }

    public CustomConnectionFactory() throws DatabaseException {
    }

    @Override
    public Connection getConnection(String url, String user, String password) throws DatabaseException {
        if (!url.isEmpty()) {
            this.dburl = url;
        }
        if (!user.isEmpty()) {
            this.user = user;
        }
        if (!password.isEmpty()) {
            this.password = password;
        }
        Connection my_connection = null;
        try {
            Class.forName(this.driver);
            my_connection = DriverManager.getConnection(this.dburl, this.user, this.password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        } catch (ClassNotFoundException e) {
            System.out.println("Error: unable to load driver class!");
            System.exit(1);
        }
        return my_connection;
    }
}