package com.example.demo.form;

public class UserLoginForm {
    private String email;
    private String password;

    public UserLoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
