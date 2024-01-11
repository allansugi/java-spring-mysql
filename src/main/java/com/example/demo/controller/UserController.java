package com.example.demo.controller;

import com.example.demo.exception.AuthenticationException;
import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.form.UserLoginForm;
import com.example.demo.form.UserRegisterForm;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Account;

public interface UserController {
    public void register(@RequestBody UserRegisterForm form) throws DatabaseErrorException;

    public void login(@RequestBody UserLoginForm form) throws DatabaseErrorException, AuthenticationException;
}
