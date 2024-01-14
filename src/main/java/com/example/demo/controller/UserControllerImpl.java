package com.example.demo.controller;

import com.example.demo.exception.AuthenticationException;
import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.form.UserLoginForm;
import com.example.demo.form.UserRegisterForm;
import com.example.demo.form.updateUser.UpdateUserEmailForm;
import com.example.demo.form.updateUser.UpdateUserNameForm;
import com.example.demo.form.updateUser.UpdateUserPasswordForm;
import com.example.demo.service.UserServiceImpl;
import com.example.demo.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

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
    public void login(@RequestBody UserLoginForm form) throws DatabaseErrorException, AuthenticationException {
        String token = this.service.authenticate(form);
        HttpHeaders header = new HttpHeaders();
        header.add("token", token);
        return ResponseEntity
    }

    @PutMapping("/update/username")
    @Override
    public void updateUsername(UpdateUserNameForm form) throws DatabaseErrorException {
        this.service.updateUsername(form.getId(), form.getUsername());
    }

    @PutMapping("/update/email")
    @Override
    public void updateUserEmail(UpdateUserEmailForm form) throws DatabaseErrorException {
        this.service.updateEmail(form.getId(), form.getEmail());
    }

    @PutMapping("/update/password")
    @Override
    public void updateUserPassword(UpdateUserPasswordForm form) throws DatabaseErrorException {
        this.service.updatePassword(form.getId(), form.getPassword());
    }

}
