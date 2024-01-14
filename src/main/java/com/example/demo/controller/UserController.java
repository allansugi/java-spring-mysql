package com.example.demo.controller;

import com.example.demo.exception.AuthenticationException;
import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.form.UserLoginForm;
import com.example.demo.form.UserRegisterForm;
import com.example.demo.form.updateUser.UpdateUserEmailForm;
import com.example.demo.form.updateUser.UpdateUserForm;
import com.example.demo.form.updateUser.UpdateUserNameForm;
import com.example.demo.form.updateUser.UpdateUserPasswordForm;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

public interface UserController {
    public void register(@RequestBody UserRegisterForm form) throws DatabaseErrorException;

    public void login(@RequestBody UserLoginForm form) throws DatabaseErrorException, AuthenticationException;
    public void updateUsername(@RequestBody UpdateUserNameForm form) throws DatabaseErrorException;
    public void updateUserEmail(@RequestBody UpdateUserEmailForm form) throws DatabaseErrorException;
    public void updateUserPassword(@RequestBody UpdateUserPasswordForm form) throws DatabaseErrorException;
}
