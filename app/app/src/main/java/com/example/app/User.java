package com.example.app;

public class User {
    String name;
    String email;
    String hashedPW;

    public User(String name, String email, String hashedPW) {
        this.name = name;
        this.email = email;
        this.hashedPW = hashedPW;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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