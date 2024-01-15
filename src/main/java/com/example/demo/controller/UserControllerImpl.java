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
import com.example.demo.util.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        response.setResponse("account registration success");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @ResponseBody
    @Override
    public ResponseEntity<Response<String>> login(@RequestBody UserLoginForm form, HttpServletResponse res) throws DatabaseErrorException, AuthenticationException {
        String token = this.service.authenticate(form);
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        res.addCookie(cookie);
        Response<String> response = new Response<>();
        response.setSuccess(true);
        response.setResponse("login success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/username")
    @ResponseBody
    @Override
    public ResponseEntity<Response<String>> updateUsername(@CookieValue("token") String token, @RequestBody UpdateUserNameForm form) throws DatabaseErrorException {
        System.out.println("token: " + token);
        String id = JWTUtil.verifyToken(token);
        System.out.println("id: " + id);
        this.service.updateUsername(id, form.getNewUsername());
        Response<String> response = new Response<>();
        response.setSuccess(true);
        response.setResponse("username has been updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/email")
    @ResponseBody
    @Override
    public ResponseEntity<Response<String>> updateUserEmail(@CookieValue("token") String token, @RequestBody UpdateUserEmailForm form) throws DatabaseErrorException {
        String id = JWTUtil.verifyToken(token);
        this.service.updateEmail(id, form.getNewEmail());
        Response<String> response = new Response<>();
        response.setSuccess(true);
        response.setResponse("email has been updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/password")
    @ResponseBody
    @Override
    public ResponseEntity<Response<String>> updateUserPassword(@CookieValue("token") String token, @RequestBody UpdateUserPasswordForm form) throws DatabaseErrorException {
        String id = JWTUtil.verifyToken(token);
        this.service.updatePassword(id, form.getNewPassword());
        Response<String> response = new Response<>();
        response.setSuccess(true);
        response.setResponse("password has been updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
