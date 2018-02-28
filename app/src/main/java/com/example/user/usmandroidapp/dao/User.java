package com.example.user.usmandroidapp.dao;

import java.util.List;

public class User {
    private String username;
    private String password;
    private List<String> unreadMessages;
    private List<String> readMessages;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getUnreadMessages() {
        return unreadMessages;
    }

    public void setUnreadMessages(List<String> unreadMessages) {
        this.unreadMessages = unreadMessages;
    }

    public List<String> getReadMessages() {
        return readMessages;
    }

    public void setReadMessages(List<String> readMessages) {
        this.readMessages = readMessages;
    }
}
