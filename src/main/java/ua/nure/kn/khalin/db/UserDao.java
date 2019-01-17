package main.java.ua.nure.kn.khalin.db;

import main.java.ua.nure.kn.khalin.User;

import java.util.Collection;

public interface UserDao {
    public User create(User userToInsert) throws DatabaseException;
    public boolean deleteUser(User userToDelete) throws DatabaseException;
    public User findUser(Long id) throws DatabaseException;
    public Collection findAll() throws DatabaseException;
    public boolean updateUser(User userToUpdate) throws DatabaseException;
    public void setConnectionFactory(ConnectionFactory connectionFactory);
}