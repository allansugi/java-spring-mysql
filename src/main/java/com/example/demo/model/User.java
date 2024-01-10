package com.example.demo.model;

import com.example.demo.form.UserRegisterForm;

import java.util.UUID;

public class User {
    private String id;
    private String email;
    private String username;
    private String password;

    public User() {

    }

    public User(UserRegisterForm form) {
        UUID uuid = UUID.randomUUID();
        setId(uuid.toString());
        setUsername(form.getUsername());
        setEmail(form.getEmail());
        setPassword(form.getPassword());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
