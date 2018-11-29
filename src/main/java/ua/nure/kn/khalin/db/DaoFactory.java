package main.java.ua.nure.kn.khalin.db;

import java.io.IOException;
import java.util.Properties;

public class DaoFactory {

    private static final DaoFactory INSTANCE = new DaoFactory();

    private final Properties properties;
    private static final String USER_DAO_CLASS_KEY = "main.java.ua.nure.kn.khalin.db.UserDao";

    private DaoFactory() {
        this.properties = new Properties();
        try {
            this.properties.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    private ConnectionFactory getConnectionFactory() {
        String driver = this.properties.getProperty("connection.driver");
        String url = this.properties.getProperty("connection.url");
        String user = this.properties.getProperty("connection.user");
        String password = this.properties.getProperty("connection.password");
        return new HConnectionFactory(driver, url, user, password);
    }

    public UserDao getUserDao() {
        try {
            Class userDaoClass = Class.forName(this.properties.getProperty(USER_DAO_CLASS_KEY));

            UserDao userDao = (UserDao) userDaoClass.newInstance();
            userDao.setConnectionFactory(this.getConnectionFactory());

            return userDao;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}