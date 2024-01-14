package com.example.demo.controller;

import com.example.demo.exception.AuthenticationException;
import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.form.UserLoginForm;
import com.example.demo.form.UserRegisterForm;
import com.example.demo.form.updateUser.UpdateUserEmailForm;
import com.example.demo.form.updateUser.UpdateUserNameForm;
import com.example.demo.form.updateUser.UpdateUserPasswordForm;
import com.example.demo.response.Response;
import com.example.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ResponseBody
    @Override
    public ResponseEntity<Response<String>> register(@RequestBody UserRegisterForm form) throws DatabaseErrorException {
        service.addUser(form);
        Response<String> response = new Response<>();
        response.setSuccess(true);
        response.setResponse("login success");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @ResponseBody
    @Override
    public ResponseEntity<Response<String>> login(@RequestBody UserLoginForm form) throws DatabaseErrorException, AuthenticationException {
        String token = this.service.authenticate(form);
        HttpHeaders header = new HttpHeaders();
        header.add("token", token);
        Response<String> response = new Response<>();
        response.setSuccess(true);
        response.setResponse("login success");
        return new ResponseEntity<>(response, header, HttpStatus.OK);
    }

    @PutMapping("/update/username")
    @ResponseBody
    @Override
    public ResponseEntity<Response<String>> updateUsername(@RequestHeader("token") String token, UpdateUserNameForm form) throws DatabaseErrorException {
        this.service.updateUsername(token, form.getNewUsername());
        Response<String> response = new Response<>();
        response.setSuccess(true);
        response.setResponse("username has been updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/email")
    @ResponseBody
    @Override
    public ResponseEntity<Response<String>> updateUserEmail(@RequestHeader("token") String token, UpdateUserEmailForm form) throws DatabaseErrorException {
        this.service.updateEmail(token, form.getNewEmail());
        Response<String> response = new Response<>();
        response.setSuccess(true);
        response.setResponse("email has been updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/password")
    @ResponseBody
    @Override
    public ResponseEntity<Response<String>> updateUserPassword(@RequestHeader("token") String token, UpdateUserPasswordForm form) throws DatabaseErrorException {
        this.service.updatePassword(token, form.getNewPassword());
        Response<String> response = new Response<>();
        response.setSuccess(true);
        response.setResponse("password has been updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
