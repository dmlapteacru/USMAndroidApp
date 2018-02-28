package com.example.user.usmandroidapp.dao;

import java.util.List;

public class UserService implements UserDAO {
    @Override
    public User findUserByUsername(String username) {
        return null;
    }

    @Override
    public List<User> findAllUsers() {
        return null;
    }

    @Override
    public List<String> findUnreadMessagesOfUser(String username) {
        return null;
    }

    @Override
    public List<String> findReadMessagesOfUser(String username) {
        return null;
    }
}
