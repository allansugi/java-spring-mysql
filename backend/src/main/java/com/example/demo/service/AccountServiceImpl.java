package com.example.demo.service;

import com.example.demo.dao.AccountDaoImpl;
import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.form.AccountForm;
import com.example.demo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{
    private final AccountDaoImpl dao;

    @Autowired
    public AccountServiceImpl(AccountDaoImpl dao) {
        this.dao = dao;
    }
    @Override
    public void addAccount(AccountForm form) throws DatabaseErrorException {
        try {
            Account account = new Account(form);
            this.dao.store(account);
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

    @Override
    public void updateAccount(Account account) throws DatabaseErrorException {
        try {
            this.dao.update(account);
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

    @Override
    public void deleteAccount(String accountId) throws DatabaseErrorException {
        try {
            this.dao.delete(accountId);
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

    @Override
    public List<Account> findUserAccounts(String userId) throws DatabaseErrorException {
        try {
            List<Account> accounts = this.dao.findAll();
            return accounts.stream().filter(a -> a.getUserId().equals(userId)).toList();
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }
}
