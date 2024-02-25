package com.example.demo.controller;

import com.example.demo.exception.AuthenticationException;
import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.form.UserLoginForm;
import com.example.demo.form.UserRegisterForm;
import com.example.demo.form.updateUser.UpdateUserEmailForm;
import com.example.demo.form.updateUser.UpdateUserNameForm;
import com.example.demo.form.updateUser.UpdateUserPasswordForm;
import com.example.demo.response.Response;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

public interface UserController {
    public ResponseEntity<Response<String>> register(@RequestBody UserRegisterForm form) throws DatabaseErrorException;

    public ResponseEntity<Response<String>> login(@RequestBody UserLoginForm form, HttpServletResponse res) throws DatabaseErrorException, AuthenticationException;

    @PutMapping("/update/username")
    @ResponseBody
    ResponseEntity<Response<String>> updateUsername(@CookieValue("token") String token, @RequestBody UpdateUserNameForm form) throws DatabaseErrorException;

    @PutMapping("/update/email")
    @ResponseBody
    ResponseEntity<Response<String>> updateUserEmail(@CookieValue("token") String token, @RequestBody UpdateUserEmailForm form) throws DatabaseErrorException;

    @PutMapping("/update/password")
    @ResponseBody
    ResponseEntity<Response<String>> updateUserPassword(@CookieValue("token") String token, @RequestBody UpdateUserPasswordForm form) throws DatabaseErrorException;
}
