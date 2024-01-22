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

    /**
     * add new account
     * @param token JWT token
     * @param form account form
     * @return response entity success message
     * @throws DatabaseErrorException if there is an internal database error
     */
    @PostMapping("/add")
    @Override
    public ResponseEntity<Response<String>> insertNewAccount(@CookieValue String token, AccountForm form) throws DatabaseErrorException {
        Response<String> response = this.service.addAccount(token, form);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * update account information
     * @param token JWT token
     * @param account account information
     * @return response entity success message
     * @throws DatabaseErrorException if there is an internal database error
     */
    @PutMapping("/update")
    @Override
    public ResponseEntity<Response<String>> updateExistingAccount(@CookieValue String token, @RequestBody Account account) throws DatabaseErrorException {
        Response<String> response = this.service.updateAccount(token, account);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * delete specified account
     * @param token JWT token
     * @param accountId account id
     * @return response entity success message
     * @throws DatabaseErrorException if there is an internal database error
     */
    @DeleteMapping("/delete/{accountId}")
    @Override
    public ResponseEntity<Response<String>> deleteAccount(@CookieValue String token, @PathVariable String accountId) throws DatabaseErrorException {
        Response<String> response = this.service.deleteAccount(token, accountId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * find account associated with user account
     * @param token JWT token
     * @return response entity success message
     * @throws DatabaseErrorException if there is an internal database error
     * @throws NoAccountFoundException if no account found
     */
    @GetMapping("/find/accounts")
    @Override
    public ResponseEntity<Response<List<Account>>> findUserAccounts(@CookieValue String token) throws DatabaseErrorException, NoAccountFoundException {
        Response<List<Account>> response= this.service.findUserAccounts(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
