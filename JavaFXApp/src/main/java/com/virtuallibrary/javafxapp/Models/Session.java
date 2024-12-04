package com.virtuallibrary.javafxapp.Models;

public class Session {

    private static User user;

    public Session() {

    }

    public Session(User user) {
        Session.user = user;
    }

    public static User getUser() {
        return user;
    }

    public static void logout(){
        user = null;
    }

    public static void login(String username, String password) {
        user = new User();
        user.setUsername(username);
        user.setPassword(password);
    }
}
