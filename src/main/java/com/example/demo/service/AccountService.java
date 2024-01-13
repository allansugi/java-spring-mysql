package com.example.demo.service;

import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.form.AccountForm;
import com.example.demo.model.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {
    public void addAccount(AccountForm form) throws DatabaseErrorException;
    public void updateAccount(Account account) throws DatabaseErrorException;
    public void deleteAccount(String accountId) throws DatabaseErrorException;
    public List<Account> findUserAccounts(String userId) throws DatabaseErrorException;
}
