package main.java.ua.nure.kn.khalin.db;

import main.java.ua.nure.kn.khalin.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MockUserDao implements UserDao {

    private Map<Long, User> users;

    public MockUserDao() {
        users = new HashMap<>();
    }

    @Override
    public User create(User user) throws DatabaseException {
        long id = users.size() + 1;
        users.put(id, user);
        user.setId(id);
        return user;
    }

    @Override
    public boolean updateUser(User user) throws DatabaseException {
        users.put(user.getId(), user);
        return true;
    }

    @Override
    public boolean deleteUser(User user) throws DatabaseException {
        users.remove(user.getId());
        return true;
    }

    @Override
    public User findUser(Long id) throws DatabaseException {
        return users.get(id);
    }

    @Override
    public Collection<User> findAll() throws DatabaseException {
        return users.values();
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {

    }
}