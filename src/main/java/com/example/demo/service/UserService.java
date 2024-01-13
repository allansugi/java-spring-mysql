package com.example.demo.service;

import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.form.UserLoginForm;
import com.example.demo.form.UserRegisterForm;
import com.example.demo.model.Account;
import com.example.demo.model.User;

import java.util.List;

public interface UserService {
    public void addUser(UserRegisterForm form) throws DatabaseErrorException;
    public Boolean authenticate(UserLoginForm form) throws DatabaseErrorException;
    public void updatePassword() throws DatabaseErrorException;
    public void updateUsername() throws DatabaseErrorException;
    public void updateEmail() throws DatabaseErrorException;
}
