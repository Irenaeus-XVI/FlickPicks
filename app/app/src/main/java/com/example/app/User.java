package com.example.app;

public class User {
    String username;
    String email;
    String hashedPW;

    public User(String name, String email, String hashedPW) {
        this.username = name;
        this.email = email;
        this.hashedPW = hashedPW;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPW() {
        return hashedPW;
    }

    public void setHashedPW(String hashedPW) {
        this.hashedPW = hashedPW;
    }
}