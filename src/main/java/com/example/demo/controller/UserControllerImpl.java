package com.example.demo.controller;

import com.example.demo.dao.UserDaoImpl;
import com.example.demo.exception.AuthenticationException;
import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.form.UserLoginForm;
import com.example.demo.form.UserRegisterForm;
import com.example.demo.model.User;
import com.example.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RequestMapping("/api/user")
@RestController
public class UserControllerImpl implements UserController {

    private final UserServiceImpl service;

    @Autowired
    public UserControllerImpl(UserServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/register")
    @Override
    public void register(@RequestBody UserRegisterForm form) throws DatabaseErrorException {
        // TODO Auto-generated method stub
        service.addUser(form);
    }

    @PostMapping("/login")
    @Override
    public void login(UserLoginForm form) throws DatabaseErrorException, AuthenticationException {
        Boolean authenticate = this.service.authenticate(form);
        if (!authenticate) {
            throw new AuthenticationException("incorrect email or password");
        }
    }

}
