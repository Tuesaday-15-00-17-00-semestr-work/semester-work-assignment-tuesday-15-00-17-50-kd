package com.virtuallibrary.springbootapi.service;

import com.virtuallibrary.springbootapi.api.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private final DatabaseService db;

    public UserService() {
        db = new DatabaseService();
    }

    public User createUser(User user) throws SQLException {
        if(db.getUserByUsername(user.getUsername()) == null) {
            return db.createUser(user);
        }

        return null;
    }

    public User getUserById(int userId) throws SQLException {
        return db.getUserById(userId);
    }

    public User getUserByUsername(String username) throws SQLException {
        return db.getUserByUsername(username);
    }

    public List<User> getAllUsers() throws SQLException {
        return db.getAllUsers();
    }

    public boolean updateUser(User user) throws SQLException {
        return db.updateUser(user);
    }

    public boolean deleteUser(int userId) throws SQLException {
        return db.deleteUser(userId);
    }

}
