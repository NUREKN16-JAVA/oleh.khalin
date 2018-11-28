package main.java.ua.nure.kn.khalin.db;

import java.io.IOException;
import java.util.Properties;

public class DaoFactory {
    public static final String USER = "connection.user";
    public static final String PASSWORD = "connection.password";
    public static final String URL = "connection.url";
    public static final String DRIVER = "connection.driver";
    public static final String USER_DAO = "main.java.ua.nure.kn.khalin.db.UserDao";

    public String user_property;
    public String password_property;
    public String url_property;
    public String driver_property;
    public String user_dao_class_property;

    private final Properties properties;

    private static final DaoFactory INSTANCE = new DaoFactory();

    private DaoFactory() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));
            user_property = properties.getProperty(USER);
            password_property = properties.getProperty(PASSWORD);
            url_property = properties.getProperty(URL);
            driver_property = properties.getProperty(DRIVER);
            user_dao_class_property = properties.getProperty(USER_DAO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ConnectionFactory getConnectionFactory() throws DatabaseException{
        CustomConnectionFactory factoryToReturn = null;
        try {
            factoryToReturn = new CustomConnectionFactory();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        if (factoryToReturn != null) {
            return  factoryToReturn;
        }
        else throw new DatabaseException("Factory is not initialized(null)");
    }

    public UserDao getUserDao() throws DatabaseException{
        Class buff;
        UserDao userDao = null;
        try {
            buff = Class.forName(user_dao_class_property);
            userDao = (UserDao) buff.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        userDao.setConnectionFactory(getConnectionFactory());
        return userDao;
    }

    public static DaoFactory getInstance() {
        return INSTANCE;
    }
}
