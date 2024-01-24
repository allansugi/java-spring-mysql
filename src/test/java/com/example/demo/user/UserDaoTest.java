package com.example.demo.user;


import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.demo.dao.UserDaoImpl;
import com.example.demo.db.DBConnectionProvider;
import com.example.demo.model.User;


@Testcontainers
public class UserDaoTest {

     @Container
     private final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
     .withExposedPorts(3306)
     .withEnv("MYSQL_ROOT_PASSWORD", "password")
     .withEnv("MYSQL_DATABASE", "app")
     .withInitScript("schema.sql");

    private UserDaoImpl dao;

    @BeforeEach
    public void BeforeEach() {
        this.dao = new UserDaoImpl(
            new DBConnectionProvider(
                mysql.getJdbcUrl(),
                mysql.getUsername(),
                mysql.getPassword()
            )
        );
    }

    @Test
    @DisplayName("store user into database")
    public void store_success() throws Exception {
        String email = "new@email.com";
        String password = "_123QWERtY";
        String user = "user";
        String id = UUID.randomUUID().toString();

        User registeredUser = new User(id, email, user, password);

        dao.store(registeredUser);
        List<User> users = dao.findAll();
        assertEquals(users.size(), 1);
    }

    @Test
    @DisplayName("find matching id")
    public void findById_success() throws Exception {
        String email = "new@email.com";
        String password = "_123QWERtY";
        String username = "user";
        String id = UUID.randomUUID().toString();

        User registeredUser = new User(id, email, username, password);

        dao.store(registeredUser);
        User userFound = dao.findById(id);
        Assertions.assertNotEquals(userFound, null);
        assertEquals(userFound.getEmail(), email);
        assertEquals(userFound.getUsername(), username);
        assertEquals(userFound.getPassword(), password);
    }

    @Test
    @DisplayName("delete matching id")
    public void delete_success() throws Exception {
        String email = "new@email.com";
        String password = "_123QWERtY";
        String username = "user";
        String id = UUID.randomUUID().toString();

        User registeredUser = new User(id, email, username, password);

        dao.store(registeredUser);
        dao.delete(id);

        List<User> users = dao.findAll();
        Assertions.assertNotEquals(users.size(), 1);
        assertEquals(users.size(), 0);
    }


    @Test
    @DisplayName("update username")
    public void updateUsername_success() throws Exception {
        String email = "new@email.com";
        String password = "_123QWERtY";
        String username = "user";
        String newUsername = "new_username";
        String id = UUID.randomUUID().toString();

        User registeredUser = new User(id, email, username, password);

        dao.store(registeredUser);

        dao.updateUsername(newUsername, id);

        User user = dao.findById(id);
        Assertions.assertNotEquals(user.getUsername(), username);
        assertEquals(user.getUsername(), newUsername);
    }

    @Test
    @DisplayName("update email")
    public void updateEmail_success() throws Exception {
        String email = "new@email.com";
        String password = "_123QWERtY";
        String username = "user";
        String newEmail = "newemail@gmail.com";
        String id = UUID.randomUUID().toString();

        User registeredUser = new User(id, email, username, password);

        dao.store(registeredUser);

        dao.updateEmail(newEmail, id);

        User user = dao.findById(id);
        Assertions.assertNotEquals(user.getEmail(), email);
        assertEquals(user.getEmail(), newEmail);
    }

}
