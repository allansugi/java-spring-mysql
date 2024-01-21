package com.example.demo.controller;

import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.exception.NoAccountFoundException;
import com.example.demo.form.AccountForm;
import com.example.demo.model.Account;
import com.example.demo.response.Response;
import com.example.demo.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/account")
@RestController
public class AccountControllerImpl implements AccountController{
    private final AccountServiceImpl service;

    @Autowired
    public AccountControllerImpl(AccountServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/add")
    @Override
    public ResponseEntity<Response<String>> insertNewAccount(AccountForm form) throws DatabaseErrorException {
        Response<String> response = this.service.addAccount(form);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update")
    @Override
    public ResponseEntity<Response<String>> updateExistingAccount(@RequestBody Account account) throws DatabaseErrorException {
        Response<String> response = this.service.updateAccount(account);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{accountId}")
    @Override
    public ResponseEntity<Response<String>> deleteAccount(@PathVariable String accountId) throws DatabaseErrorException {
        Response<String> response = this.service.deleteAccount(accountId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/find/{userId}")
    @Override
    public ResponseEntity<Response<List<Account>>> findUserAccounts(@PathVariable String userId) throws DatabaseErrorException, NoAccountFoundException {
        Response<List<Account>> response= this.service.findUserAccounts(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
