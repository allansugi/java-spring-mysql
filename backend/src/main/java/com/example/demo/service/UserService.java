package com.example.demo.service;

import com.example.demo.exception.AuthenticationException;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.exception.RegisterArgumentException;
import com.example.demo.form.UserLoginForm;
import com.example.demo.form.UserRegisterForm;
import com.example.demo.response.Response;

public interface UserService {
    Response<String> addUser(UserRegisterForm form) throws DatabaseErrorException, RegisterArgumentException, BadRequestException;
    Response<String> authenticate(UserLoginForm form) throws DatabaseErrorException, AuthenticationException;
    Response<String> updatePassword(String token, String password) throws DatabaseErrorException, BadRequestException;
    Response<String> updateUsername(String token, String username) throws DatabaseErrorException;
    Response<String> updateEmail(String token, String email) throws DatabaseErrorException, RegisterArgumentException;
}
