package main.java.ua.nure.kn.khalin.db;

import java.io.IOException;
import java.util.Properties;

public abstract class DaoFactory {
    public static final String USER = "connection.user";
    public static final String PASSWORD = "connection.password";
    public static final String URL = "connection.url";
    public static final String DRIVER = "connection.driver";
    protected static final String USER_DAO = "main.java.ua.nure.kn.khalin.db.UserDao";

    private static DaoFactory instance;

    protected static Properties properties;

    private static DaoFactory INSTANCE;
    private static final String DAO_FACTORY = "dao.factory";
    private static final String PROPERTIES_FILE = "settings.properties";

    static {
        properties = new Properties();
        try {
            properties.load(DaoFactory.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    protected DaoFactory() {
    }

    protected ConnectionFactory getConnectionFactory() throws DatabaseException {
        CustomConnectionFactory factoryToReturn = null;
        try {
            factoryToReturn = new CustomConnectionFactory();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        if (factoryToReturn != null) {
            return factoryToReturn;
        } else throw new DatabaseException("Factory is not initialized(null)");
    }

    public static void init(Properties initProperties) {
        properties = initProperties;
        instance = null;
    }

    public abstract UserDao getUserDao() throws DatabaseException;

    public static synchronized DaoFactory getInstance() {
        if (INSTANCE == null) {
            try {
                Class factoryClass = Class.forName(properties
                        .getProperty(DAO_FACTORY));
                INSTANCE = (DaoFactory) factoryClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return INSTANCE;
    }
}