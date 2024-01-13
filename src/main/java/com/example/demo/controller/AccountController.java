package com.example.demo.controller;

import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.form.AccountForm;
import com.example.demo.model.Account;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AccountController {
    public void insertNewAccount(@RequestBody AccountForm form) throws DatabaseErrorException;
    public void updateExistingAccount(@RequestBody Account account) throws DatabaseErrorException;
    public void deleteAccount(String accountId) throws DatabaseErrorException;
    public List<Account> findUserAccounts(String userId) throws DatabaseErrorException;
}
