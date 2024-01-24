package com.example.demo.account;

import com.example.demo.dao.AccountDaoImpl;
import com.example.demo.dao.UserDaoImpl;
import com.example.demo.db.DBConnectionProvider;
import com.example.demo.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Note:
 * Ideally we want to set userId for the account, however due to foreign key constraint
 * from MySQL schema, we left it null for easier unit testing.
 * userId will be included for Integration testing
 */
@Testcontainers
public class AccountDaoTest {
    @Container
    private final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withExposedPorts(3306)
            .withEnv("MYSQL_ROOT_PASSWORD", "password")
            .withEnv("MYSQL_DATABASE", "app")
            .withInitScript("schema.sql");

    private AccountDaoImpl dao;
    private UserDaoImpl userDao;

    @BeforeEach
    public void BeforeEach() {
        this.dao = new AccountDaoImpl(
                new DBConnectionProvider(
                        mysql.getJdbcUrl(),
                        mysql.getUsername(),
                        mysql.getPassword()
                )
        );
    }

    @Test
    @DisplayName("store account into database")
    public void store_success() throws Exception {
        String id = UUID.randomUUID().toString();
        String account_name = "Github";
        String account_username = "username";
        String account_password = "password";

        Account account = new Account();
        account.setAccount_password(account_password);
        account.setAccount_name(account_name);
        account.setAccount_username(account_username);
        account.setId(id);

        dao.store(account);
        List<Account> accounts = dao.findAll();
        assertEquals(accounts.size(), 1);
    }

    @Test
    @DisplayName("account found by their id")
    public void findById_success() throws Exception {

        String id = UUID.randomUUID().toString();
        String account_name = "Github";
        String account_username = "username";
        String account_password = "password";

        Account account = new Account();
        account.setAccount_password(account_password);
        account.setAccount_name(account_name);
        account.setAccount_username(account_username);
        account.setId(id);

        dao.store(account);
        Account testAccount = dao.findById(id);

        assertEquals(testAccount.getAccount_name(), account.getAccount_name());
        assertEquals(testAccount.getAccount_password(), account.getAccount_password());
        assertEquals(testAccount.getAccount_username(), account.getAccount_username());
        assertEquals(testAccount.getId(), account.getId());
    }
}
