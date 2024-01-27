package com.example.demo;

import com.example.demo.form.UserRegisterForm;
import com.example.demo.response.Response;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWebTestClient
//@Testcontainers
@Ignore
class DemoApplicationTests {

//	static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
//     .withExposedPorts(3306)
//     .withEnv("MYSQL_ROOT_PASSWORD", "password")
//     .withEnv("MYSQL_DATABASE", "app")
//     .withInitScript("schema.sql");
//
//	 @DynamicPropertySource
//	static void configureProperties(DynamicPropertyRegistry registry) {
//		registry.add("spring.datasource.url", mysql.getJdbcUrl());
//		registry.add("spring.datasource.username", mysql.getUsername());
//		registry.add("spring.datasource.password", mysql.getPassword());
//	}
//
//	@LocalServerPort
//	private int port;
//
//	@Autowired
//	private WebTestClient webTestClient;
//
//	@Test
//	void contextLoads() {
//	}
//
//	@Test
//	@DisplayName("insert user account valid")
//	void insertAccount() throws Exception {
//		UserRegisterForm form =  new UserRegisterForm("user1", "user1@gmail.com", "_123QWERtY");
//
//		webTestClient.post().uri("/api/user/register")
//		.accept(MediaType.APPLICATION_JSON)
//		.bodyValue(form)
//		.exchange()
//		.expectStatus().isCreated();
//	}
}
