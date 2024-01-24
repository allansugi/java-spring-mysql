package com.example.demo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.example.demo.exception.DatabaseErrorException;

@Component
public class DBConnectionProvider {
    private final String url;
    private final String username;
    private final String password;

    public DBConnectionProvider(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public DBConnectionProvider() {
        this.url = "jdbc:mysql://db:3306/app";
        this.username = "root";
        this.password = "password";
    }

    public Connection getConnection() throws DatabaseErrorException {
        try {
            return DriverManager.getConnection(this.url, this.username, this.password);
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }
}
