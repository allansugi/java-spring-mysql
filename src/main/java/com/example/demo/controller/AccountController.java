package com.example.demo.controller;

import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.form.AccountForm;
import com.example.demo.model.Account;
import com.example.demo.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AccountController {
    public ResponseEntity<Response<String>> insertNewAccount(@RequestBody AccountForm form) throws DatabaseErrorException;
    public ResponseEntity<Response<String>> updateExistingAccount(@RequestBody Account account) throws DatabaseErrorException;
    public ResponseEntity<Response<String>> deleteAccount(String accountId) throws DatabaseErrorException;
    public ResponseEntity<Response<List<Account>>> findUserAccounts(String userId) throws DatabaseErrorException;
}
