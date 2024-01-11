package com.example.demo.form;

public class AccountForm {
    private String account_name;
    // can be username or email
    private String account_username;

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_username() {
        return account_username;
    }

    public void setAccount_username(String account_username) {
        this.account_username = account_username;
    }

    public String getAccount_password() {
        return account_password;
    }

    public void setAccount_password(String account_password) {
        this.account_password = account_password;
    }

    private String account_password;
}
