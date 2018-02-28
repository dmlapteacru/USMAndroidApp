package com.example.user.usmandroidapp.dao;

import java.util.List;

public interface UserDAO {
    User findUserByUsername(String username);
    List<User> findAllUsers();
    List<String> findUnreadMessagesOfUser(String username);
    List<String> findReadMessagesOfUser(String username);
}
