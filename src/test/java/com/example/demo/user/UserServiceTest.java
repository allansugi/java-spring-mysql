package com.example.demo.user;

import com.example.demo.dao.UserDaoImpl;
import com.example.demo.exception.AuthenticationException;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.RegisterArgumentException;
import com.example.demo.form.UserLoginForm;
import com.example.demo.form.UserRegisterForm;
import com.example.demo.model.User;
import com.example.demo.response.Response;
import com.example.demo.service.UserServiceImpl;
import com.example.demo.util.JWTUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * test user service class
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl service;
    @Mock
    private UserDaoImpl dao;
    @Mock
    private JWTUtil util;

    @Test
    public void addUser_success() throws Exception {
        Response<String> response = new Response<>();
        response.setSuccess(true);
        response.setResponse("registration successful");

        UserRegisterForm form = new UserRegisterForm();
        form.setEmail("new@gmail.com");
        form.setUsername("user");
        form.setPassword("_123QWERtY");

        Response<String> testResponse = service.addUser(form);

        assertEquals(testResponse.getSuccess(), response.getSuccess());
        assertEquals(testResponse.getResponse(), response.getResponse());
    }

    @Test
    public void addUser_invalid_password() throws Exception {
        UserRegisterForm form = new UserRegisterForm();
        form.setEmail("new@gmail.com");
        form.setUsername("user");
        form.setPassword("_123QWERTY");
        assertThrows(BadRequestException.class, () -> service.addUser(form));
    }

    @Test
    public void addUser_email_exist() throws Exception {
        UserRegisterForm form = new UserRegisterForm();
        form.setEmail("new@gmail.com");
        form.setUsername("user");
        form.setPassword("_123QWERtY");

        User DuplicateEmail = new User("id", "new@gmail.com", "user", "anyPassword");

        // password check involves findAll
        when(dao.findAll()).thenReturn(List.of(DuplicateEmail));
        assertThrows(RegisterArgumentException.class, () -> service.addUser(form));
    }

    @Test
    public void authenticate_success() throws Exception {
        PasswordEncoder encoderMock = new BCryptPasswordEncoder();

        String password = "password";
        String user = "user";
        String email = "new@email.com";
        String id = UUID.randomUUID().toString();
        String hashedPassword = encoderMock.encode(password);

        User registeredUser = new User(id, email, user, hashedPassword);
        UserLoginForm form = new UserLoginForm(email, password);

        when(dao.findAll()).thenReturn(List.of(registeredUser));

        Response<String> response = new Response<>();
        response.setSuccess(true);
        response.setResponse("we don't test token");

        Response<String> testResponse = service.authenticate(form);

        assertEquals(testResponse.getSuccess(), response.getSuccess());
    }

    @Test
    public void authenticate_fail_password() throws Exception {
        PasswordEncoder encoderMock = new BCryptPasswordEncoder();

        String password = "password";
        String otherPassword = "otherpassword";
        String hashedPassword = encoderMock.encode(otherPassword);

        User registeredUser = new User("1ibfwu9o23b", "new@email.com", "user", hashedPassword);

        when(dao.findAll()).thenReturn(List.of(registeredUser));

        UserLoginForm form = new UserLoginForm("new@email.com", password);

        assertThrows(AuthenticationException.class, () -> service.authenticate(form));
    }

    @Test
    public void authenticate_fail_email() throws Exception {
        PasswordEncoder encoderMock = new BCryptPasswordEncoder();

        String user = "user";
        String id = UUID.randomUUID().toString();
        String email1 = "new1@email.com";
        String email2 = "new2@email.com";
        String password = "password";
        String hashedPassword = encoderMock.encode(password);

        User registeredUser = new User(id, email1, user, hashedPassword);
        when(dao.findAll()).thenReturn(List.of(registeredUser));

        UserLoginForm form = new UserLoginForm(email2, password);

        assertThrows(AuthenticationException.class, () -> service.authenticate(form));
    }

    @Test
    public void updatePassword_success() throws Exception {
        String mockToken = "123b7fqw9";
        String id = UUID.randomUUID().toString();
        String password = "_123QWERtY";

        Response<String> response = new Response<>();
        response.setSuccess(true);
        response.setResponse("password updated");

        when(util.verifyToken(mockToken)).thenReturn(id);

        Response<String> testResponse = service.updatePassword(mockToken, password);

        assertEquals(testResponse.getSuccess(), response.getSuccess());
        assertEquals(testResponse.getResponse(), response.getResponse());
    }

    @Test
    public void updatePassword_fail_password() throws Exception {
        String password = "_123QWERTY";
        String mockToken = "123b7fqw9";

        assertThrows(BadRequestException.class, () -> service.updatePassword(mockToken, password));
    }

    @Test
    public void updateEmail_success() throws Exception {
        String mockToken = "123b7fqw9";
        String email = "new@email.com";
        String newEmail = "newer@email.com";
        String password = "_123QWERtY";
        String user = "user";
        String id = UUID.randomUUID().toString();

        User registeredUser = new User(id, email, user, password);

        Response<String> response = new Response<>();
        response.setSuccess(true);
        response.setResponse("email updated");

        when(dao.findAll()).thenReturn(List.of(registeredUser));

        Response<String> testResponse = service.updateEmail(mockToken, newEmail);

        assertEquals(testResponse.getSuccess(), response.getSuccess());
        assertEquals(testResponse.getResponse(), response.getResponse());
    }

    @Test
    public void updateEmail_fail_same_email() throws Exception {
        String mockToken = "123b7fqw9";
        String email = "new@email.com";
        String password = "_123QWERtY";
        String user = "user";
        String id = UUID.randomUUID().toString();

        User registeredUser = new User(id, email, user, password);

        when(dao.findAll()).thenReturn(List.of(registeredUser));

        assertThrows(RegisterArgumentException.class, () -> service.updateEmail(mockToken, email));
    }

    @Test
    public void updateUsername_success() throws Exception {
        String mockToken = "123b7fqw9";
        String email = "new@email.com";
        String password = "_123QWERtY";
        String user = "user";
        String newUser = "userNew";
        String id = UUID.randomUUID().toString();

        User registeredUser = new User(id, email, user, password);

        Response<String> response = new Response<>();
        response.setSuccess(true);
        response.setResponse("username updated");

        when(dao.findAll()).thenReturn(List.of(registeredUser));

        Response<String> testResponse = service.updateUsername(mockToken, newUser);

        assertEquals(testResponse.getSuccess(), response.getSuccess());
        assertEquals(testResponse.getResponse(), response.getResponse());
    }

    @Test
    public void updateUsername_fail_same_username() throws Exception {
        String mockToken = "123b7fqw9";
        String email = "new@email.com";
        String password = "_123QWERtY";
        String user = "user";
        String id = UUID.randomUUID().toString();

        User registeredUser = new User(id, email, user, password);

        when(dao.findAll()).thenReturn(List.of(registeredUser));

        assertThrows(RegisterArgumentException.class, () -> service.updateUsername(mockToken, user));
    }

}
