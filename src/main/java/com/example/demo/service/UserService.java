package com.example.demo.service;

import com.example.demo.exception.AuthenticationException;
import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.form.UserLoginForm;
import com.example.demo.form.UserRegisterForm;

public interface UserService {
    public void addUser(UserRegisterForm form) throws DatabaseErrorException;
    public String authenticate(UserLoginForm form) throws DatabaseErrorException, AuthenticationException;
    public void updatePassword(String id, String password) throws DatabaseErrorException;
    public void updateUsername(String id, String username) throws DatabaseErrorException;
    public void updateEmail(String id, String email) throws DatabaseErrorException;
}
