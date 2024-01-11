package com.example.demo.dao;

import com.example.demo.model.Account;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountDaoImpl implements CrudDao<Account> {

    private final String url = "jdbc:mysql://db:3306/app";
    private final String username = "root";
    private final String password = "password";
    private final String storeQry = "INSERT INTO account (id, userId, account_name, account_username, account_password) VALUES (?, ?, ?, ?, ?)";
    private final String findAllQry = "SELECT * FROM account";
    private final String deleteQry = "DELETE FROM account where id = ?";
    private final String findIdQry = "SELECT * FROM account where id = ?";
    private final String updateQry = """
                                    UPDATE account\s
                                    SET account_name = ?,\s
                                        account_username = ?,\s
                                        account_password = ?
                                    WHERE id = ? and userId = ?
                                    """;
    @Override
    public void store(Account data) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(storeQry);
            statement.setString(1, data.getId());
            statement.setString(2, data.getUserId());
            statement.setString(3, data.getAccount_name());
            statement.setString(4, data.getAccount_username());
            statement.setString(5, data.getAccount_password());
            statement.execute();
        }
    }

    @Override
    public Account findById(String id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(findIdQry);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Account(
                    rs.getString("id"),
                    rs.getString("userId"),
                    rs.getString("account_name"),
                    rs.getString("account_username"),
                    rs.getString("account_password")
                );
            }

             return null;
        }
    }

    @Override
    public List<Account> findAll() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(findAllQry);
            while (rs.next()) {
                Account account = new Account(
                        rs.getString("id"),
                        rs.getString("userId"),
                        rs.getString("account_name"),
                        rs.getString("account_username"),
                        rs.getString("account_password")
                );
                accounts.add(account);
            }
            return accounts;
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
}
