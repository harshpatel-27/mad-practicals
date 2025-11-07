package com.example.userregistrationapp;

public class User {
    String name;
    String email;
    String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return name + "," + email + "," + password;
    }

    public static User fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 3) {
            return new User(parts[0], parts[1], parts[2]);
        }
        return null;
    }
}