package com.example.demo.user;

import com.example.demo.controller.UserController;
import com.example.demo.exception.AuthenticationException;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.RegisterArgumentException;
import com.example.demo.form.UserLoginForm;
import com.example.demo.form.UserRegisterForm;
import com.example.demo.form.updateUser.UpdateUserEmailForm;
import com.example.demo.form.updateUser.UpdateUserNameForm;
import com.example.demo.form.updateUser.UpdateUserPasswordForm;
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

/**
 * to check for correct http response from the user controller
 */
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
        doThrow(new BadRequestException("password not met the requirement")).when(service).addUser(any(UserRegisterForm.class));

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
    public void register_fail_return_409() throws Exception {
        doThrow(new RegisterArgumentException("")).when(service).addUser(any(UserRegisterForm.class));

        // Service layer test
        assertThrows(RegisterArgumentException.class, () -> service.addUser(firstUserRegister()));

        // MVC layer test
        this.mockMvc.perform(post("/api/user/register")
                        .content(mapper.writeValueAsString(firstUserRegister()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
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

    @Test
    public void update_email_success_return_200() throws Exception {
        Response<String> response = new Response<>();
        response.setSuccess(true);
        response.setResponse("email updated");

        UpdateUserEmailForm form = new UpdateUserEmailForm();
        form.setOldEmail("oldUser@gmail.com");
        form.setNewEmail("newUser@gmail.com");

        when(service.updateEmail("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c", "newUser@gmail.com")).thenReturn(response);

        Cookie cookie = new Cookie("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");

        this.mockMvc.perform(put("/api/user/update/email")
                        .cookie(cookie)
                        .content(mapper.writeValueAsString(form))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void update_password_success_return_200() throws Exception {
        Response<String> response = new Response<>();
        response.setSuccess(true);
        response.setResponse("email updated");

        UpdateUserPasswordForm form = new UpdateUserPasswordForm();
        form.setOldPassword("oldPassword");
        form.setNewPassword("newPassword");

        when(service.updatePassword("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c", "newPassword")).thenReturn(response);

        Cookie cookie = new Cookie("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");

        this.mockMvc.perform(put("/api/user/update/password")
                        .cookie(cookie)
                        .content(mapper.writeValueAsString(form))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void update_email_fail_return_409() throws Exception {
        doThrow(new RegisterArgumentException()).when(service).updateEmail("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c", "newUser@gmail.com");
        Cookie cookie = new Cookie("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
        // Service layer test
        assertThrows(RegisterArgumentException.class, () -> service.updateEmail("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c", "newUser@gmail.com"));

        UpdateUserEmailForm form = new UpdateUserEmailForm();
        form.setOldEmail("oldUser@gmail.com");
        form.setNewEmail("newUser@gmail.com");
        // MVC layer test
        this.mockMvc.perform(put("/api/user/update/email")
                        .cookie(cookie)
                        .content(mapper.writeValueAsString(form))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void update_password_fail_return_400() throws Exception {
        doThrow(new BadRequestException()).when(service).updatePassword("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c", "newPassword");
        Cookie cookie = new Cookie("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
        // Service layer test
        assertThrows(BadRequestException.class, () -> service.updatePassword("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c", "newPassword"));

        UpdateUserPasswordForm form = new UpdateUserPasswordForm();
        form.setOldPassword("oldPassword");
        form.setNewPassword("newPassword");
        // MVC layer test
        this.mockMvc.perform(put("/api/user/update/password")
                        .cookie(cookie)
                        .content(mapper.writeValueAsString(form))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
