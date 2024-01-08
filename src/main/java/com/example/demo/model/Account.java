package com.example.demo.model;

public class Account {
    private String id;
    private String account_name;
    private String account_password;

    public Account() {

    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getAccount_name() {
        return account_name;
    }
    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }
    public String getAccount_password() {
        return account_password;
    }
    public void setAccount_password(String account_password) {
        this.account_password = account_password;
    }
}
