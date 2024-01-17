package com.example.demo;

import com.example.demo.controller.UserController;
import com.example.demo.dao.UserDaoImpl;
import com.example.demo.exception.AuthenticationException;
import com.example.demo.form.UserLoginForm;
import com.example.demo.form.UserRegisterForm;
import com.example.demo.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private UserServiceImpl service;

    private UserRegisterForm firstUserRegister() {
        return new UserRegisterForm("user1", "user1@gmail.com", "password");
    }

    private UserLoginForm firstUserLogin() {
        return new UserLoginForm("user1@gmail.com", "password");
    }
    private UserLoginForm badFirstUserLogin() {
        return new UserLoginForm("u.com", "paord");
    }

    @Test
    public void register_success_return_201() throws Exception {
        this.mockMvc.perform(post("/api/user/register")
                .content(mapper.writeValueAsString(firstUserRegister()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void login_success_return_200() throws Exception {
        // assume token is any generic string
        when(service.authenticate(firstUserLogin())).thenReturn("anytoken");

        this.mockMvc.perform(post("/api/user/login")
                .content(mapper.writeValueAsString(firstUserLogin()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void login_fail_return_404() throws Exception {
        when(service.authenticate(any(UserLoginForm.class))).thenThrow(new AuthenticationException(""));
        this.mockMvc.perform(post("/api/user/login")
                        .content(mapper.writeValueAsString(firstUserLogin()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
