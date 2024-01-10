package com.example.demo.service;

import com.example.demo.form.UserRegisterForm;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.dao.UserDaoImpl;
import com.example.demo.model.Account;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserDaoImpl dao;

    @Autowired
    public UserServiceImpl(UserDaoImpl dao) {
        this.dao = dao;
    }

    private User convertToData(UserRegisterForm form) {
        return new User(form);
    }

    @Override
    public void addUser(UserRegisterForm form) {
        User user = convertToData(form);
        this.dao.store(user);
    }
    
}
