package com.example.demo.controller;

import com.example.demo.form.UserRegisterForm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Account;

public interface UserController {
    public void register(@RequestBody UserRegisterForm form);
}
