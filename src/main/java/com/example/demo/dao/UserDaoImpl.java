package com.example.demo.dao;

import java.sql.*;
import java.util.List;

import javax.sql.DataSource;

import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.demo.model.Account;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements CrudDao<User> {

    private String url = "jdbc:mysql://db:3306/app";
    private String username = "root";
    private String password = "password";
    private String storeQry = "INSERT INTO user (id, username, email, password) VALUES (?, ?, ?, ?)";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public void store(User data) {
        // TODO Auto-generated method stub
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(storeQry);
            statement.setString(1, data.getId());
            statement.setString(2, data.getUsername());
            statement.setString(3, data.getEmail());
            statement.setString(4, data.getPassword());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<User> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
    
}
