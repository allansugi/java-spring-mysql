package com.example.demo.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements CrudDao<User> {

    private final String url = "jdbc:mysql://db:3306/app";
    private final String username = "root";
    private final String password = "password";
    private final String storeQry = "INSERT INTO user (id, username, email, password) VALUES (?, ?, ?, ?)";
    private final String findAllQry = "SELECT * FROM user";
    private final String deleteQry = "DELETE FROM user where id = ?";
    private final String findIdQry = "SELECT * FROM user WHERE id = ?";
    private final String updateQry = "UPDATE user SET username = ?, email = ?, password = ? where id = ?";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public void store(User data) throws SQLException {
        // TODO Auto-generated method stub
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(storeQry);
            statement.setString(1, data.getId());
            statement.setString(2, data.getUsername());
            statement.setString(3, data.getEmail());
            statement.setString(4, data.getPassword());
            statement.execute();
        }
    }

    @Override
    public User findById(String id) throws SQLException {
        // TODO Auto-generated method stub
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(findIdQry);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getString("id"), 
                    rs.getString("email"), 
                    rs.getString("username"), 
                    rs.getString("password")
                );
            }
            return null;
        }
    }

    @Override
    public List<User> findAll() throws SQLException {
        // TODO Auto-generated method stub
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(findAllQry);
            while (rs.next()) {
                User user = new User(rs.getString("id"), rs.getString("email"),
                        rs.getString("username"), rs.getString("password"));
                users.add(user);
            }
            return users;
        }
    }

    @Override
    public void delete(String id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(deleteQry);
            statement.setString(1, id);
            statement.execute();
        }
    }

    /**
     * update entire user account data, not used
     * @param data
     * @throws SQLException
     */
    @Override
    public void update(User data) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(updateQry);
            statement.setString(1, data.getUsername());
            statement.setString(2, data.getEmail());
            statement.setString(3, data.getPassword());
            statement.setString(4, data.getId());
            statement.execute();
        }
    }

    /**
     * update username
     * @param username
     * @param id
     * @throws SQLException
     */
    public void updateUsername(String username, String id) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE user SET username = ? where id = ?");
            statement.setString(1, username);
            statement.setString(2, id);
            statement.execute();
        }
    }

    public void updateEmail(String email, String id) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE user SET username = ? where id = ?");
            statement.setString(1, email);
            statement.setString(2, id);
            statement.execute();
        }
    }

    /**
     * updated encrypted password
     * @param password
     * @param id
     * @throws SQLException
     */
    public void updatePassword(String password, String id) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE user SET username = ? where id = ?");
            statement.setString(1, password);
            statement.setString(2, id);
            statement.execute();
        }
    }

}
