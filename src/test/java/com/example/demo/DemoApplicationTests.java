package com.example.demo;

import com.example.demo.form.UserRegisterForm;
import com.example.demo.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private WebTestClient webTestClient;
	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("Register new user valid")
	void userRegisterSuccess() {
		UserRegisterForm form = new UserRegisterForm("user1", "user1@gmail.com", "_123QWERtY");
		// Define the type for the response
		ParameterizedTypeReference<Response<String>> responseType = new ParameterizedTypeReference<Response<String>>() {};

		// Make a POST request to register a new user
		EntityExchangeResult<Response<String>> result= webTestClient.post()
				.uri("/api/user/register")
				.bodyValue(form)
				.cookie("user", "john_doe") // Include your cookies here
				.exchange()
				.expectStatus().isCreated()
				.expectBody(responseType).returnResult();

		Response<String> responseBody = result.getResponseBody();
		assertNotNull(responseBody);
        assertEquals(responseBody.getSuccess(), true);
		assertEquals(responseBody.getResponse(), "registration successful");
	}
}
