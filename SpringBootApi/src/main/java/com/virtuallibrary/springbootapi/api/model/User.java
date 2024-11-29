package com.virtuallibrary.springbootapi.api.model;

public class User {
    private int userId;
    private String username;
    private int roleId;
    private String email;

    private String password;

    public User() {

    }

    public User(int userId, String username, int roleId, String email, String password) {
        this.userId = userId;
        this.username = username;
        this.roleId = roleId;
        this.email = email;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
