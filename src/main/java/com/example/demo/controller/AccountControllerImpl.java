package com.example.demo.controller;

import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.form.AccountForm;
import com.example.demo.model.Account;
import com.example.demo.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void insertNewAccount(AccountForm form) throws DatabaseErrorException {
        this.service.addAccount(form);
    }

    @PutMapping("/update")
    @Override
    public void updateExistingAccount(@RequestBody Account account) throws DatabaseErrorException {
        this.service.updateAccount(account);
    }

    @DeleteMapping("/delete/{accountId}")
    @Override
    public void deleteAccount(@PathVariable String accountId) throws DatabaseErrorException {
        this.service.deleteAccount(accountId);
    }

    @GetMapping("/find/{userId}")
    @Override
    public List<Account> findUserAccounts(@PathVariable String userId) throws DatabaseErrorException {
        return this.service.findUserAccounts(userId);
    }
}
