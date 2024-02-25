package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Dao {
    private String url = "jdbc:mysql://db:3306/app";
    private String username = "root";
    private String password = "password";
    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
