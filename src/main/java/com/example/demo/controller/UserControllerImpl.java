package com.example.demo.controller;

import com.example.demo.form.UserRegisterForm;
import com.example.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void register(@RequestBody UserRegisterForm form) {
        // TODO Auto-generated method stub
        service.addUser(form);
    }
    
}
