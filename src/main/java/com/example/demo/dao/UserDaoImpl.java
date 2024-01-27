package com.example.demo.dao;

import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements CrudDao<User> {

    private final DataSource dataSource;
    private final String storeQry = "INSERT INTO user (id, username, email, password) VALUES (?, ?, ?, ?)";
    private final String findAllQry = "SELECT * FROM user";
    private final String deleteQry = "DELETE FROM user where id = ?";
    private final String findIdQry = "SELECT * FROM user WHERE id = ?";
    private final String updateQry = "UPDATE user SET username = ?, email = ?, password = ? where id = ?";

    @Autowired
    public UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void store(User data) throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
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
        try (Connection connection = this.dataSource.getConnection()) {
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
        List<User> users = new ArrayList<>();
        try (Connection connection = this.dataSource.getConnection()) {
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
        try (Connection connection = this.dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(deleteQry);
            statement.setString(1, id);
            statement.execute();
        }
    }
    
    @Override
    public void update(User data) throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(updateQry);
            statement.setString(1, data.getUsername());
            statement.setString(2, data.getEmail());
            statement.setString(3, data.getPassword());
            statement.setString(4, data.getId());
            statement.executeUpdate();
        }
    }

    /**
     * update username
     * @param username new username
     * @param id user id
     * @return number of rows updated, either 1 or 0
     * @throws SQLException database connection error
     */
    public int updateUsername(String username, String id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("username: " + username);
            System.out.println("id: " + id);
            PreparedStatement statement = connection.prepareStatement("UPDATE user SET username = ? WHERE id = ?");
            statement.setString(1, username);
            statement.setString(2, id);
            return statement.executeUpdate();
        }
    }

    /**
     * update email
     * @param email
     * @param id
     * @return number of rows updated, either 1 or 0
     * @throws SQLException
     */
    public int updateEmail(String email, String id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE user SET email = ? WHERE id = ?");
            statement.setString(1, email);
            statement.setString(2, id);
            return statement.executeUpdate();
        }
    }

    /**
     * update password with encrypted password
     * @param password
     * @param id
     * @return number of rows updated, either 1 or 0
     * @throws SQLException
     */
    public int updatePassword(String password, String id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE user SET password = ? where id = ?");
            statement.setString(1, password);
            statement.setString(2, id);
            return statement.executeUpdate();
        }
    }

    @Override
    public void deleteAll() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM user");
        }
    }

}
