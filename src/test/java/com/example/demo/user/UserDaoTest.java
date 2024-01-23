package com.example.demo.user;

import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.MySQLContainer;

import com.example.demo.dao.UserDaoImpl;

public class UserDaoTest {
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0");
    UserDaoImpl dao;

    @BeforeAll
    static void BeforeAll() {
        mysql.start();
    }

    static void afterAll() {
        mysql.stop();
    }
}
