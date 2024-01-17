package com.example.demo;

import com.example.demo.controller.UserController;
import com.example.demo.exception.AuthenticationException;
import com.example.demo.exception.BadRequestException;
import com.example.demo.form.UserLoginForm;
import com.example.demo.form.UserRegisterForm;
import com.example.demo.form.updateUser.UpdateUserNameForm;
import com.example.demo.response.Response;
import com.example.demo.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    @Test
    public void register_success_return_201() throws Exception {
        this.mockMvc.perform(post("/api/user/register")
                .content(mapper.writeValueAsString(firstUserRegister()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void register_fail_return_400() throws Exception {
        doThrow(new BadRequestException("")).when(service).addUser(any(UserRegisterForm.class));

        // Service layer test
        assertThrows(BadRequestException.class, () -> service.addUser(firstUserRegister()));

        // MVC layer test
        this.mockMvc.perform(post("/api/user/register")
                        .content(mapper.writeValueAsString(firstUserRegister()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void login_success_return_200() throws Exception {
        Response<String> response = new Response<>();
        response.setSuccess(true);
        response.setResponse("asdsadasdasdsa");
        when(service.authenticate(any(UserLoginForm.class))).thenReturn(response);

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

    @Test
    public void update_username_success_return_200() throws Exception {
        Response<String> response = new Response<>();
        response.setSuccess(true);
        response.setResponse("username updated");

        UpdateUserNameForm form = new UpdateUserNameForm();
        form.setOldUsername("old");
        form.setOldUsername("new");

        when(service.updateUsername("id", "anotherusername")).thenReturn(response);

        Cookie cookie = new Cookie("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");

        this.mockMvc.perform(put("/api/user/update/username")
                        .cookie(cookie)
                        .content(mapper.writeValueAsString(form))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
