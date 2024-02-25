package com.example.demo.controller;

import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.exception.NoAccountFoundException;
import com.example.demo.form.AccountForm;
import com.example.demo.model.Account;
import com.example.demo.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface AccountController {
    @PostMapping("/add")
    ResponseEntity<Response<String>> insertNewAccount(@CookieValue String token, AccountForm form) throws DatabaseErrorException;

    @PutMapping("/update")
    ResponseEntity<Response<String>> updateExistingAccount(@CookieValue String token, @RequestBody Account account) throws DatabaseErrorException;

    @DeleteMapping("/delete/{accountId}")
    ResponseEntity<Response<String>> deleteAccount(@CookieValue String token, @PathVariable String accountId) throws DatabaseErrorException;

    @GetMapping("/find/account")
    ResponseEntity<Response<List<Account>>> findUserAccounts(@CookieValue String token) throws DatabaseErrorException, NoAccountFoundException;
}
