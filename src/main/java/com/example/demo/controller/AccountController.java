package com.example.demo.controller;

import com.example.demo.form.AccountForm;
import com.example.demo.model.Account;

import java.util.List;

public interface AccountController {
    public void insertNewAccount(AccountForm form);
    public void updateExistingAccount(AccountForm form);
    public void deleteAccount(String accountId);
    public List<Account> findUserAccounts(String userId);
}
