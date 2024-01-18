package com.example.demo.user;

import com.example.demo.dao.UserDaoImpl;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.RegisterArgumentException;
import com.example.demo.form.UserRegisterForm;
import com.example.demo.model.User;
import com.example.demo.response.Response;
import com.example.demo.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        User addedAccount = new User("id", "new@gmail.com", "user", BCrypt.hashpw("_123QWERtY", BCrypt.gensalt()));

    }

}
