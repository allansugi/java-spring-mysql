package com.example.demo.service;

import com.example.demo.dao.AccountDaoImpl;
import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.form.AccountForm;
import com.example.demo.model.Account;
import com.example.demo.response.Response;
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
    public Response<String> addAccount(AccountForm form) throws DatabaseErrorException {
        try {
            Account account = new Account(form);
            this.dao.store(account);
            Response<String> response = new Response<>();
            response.setSuccess(true);
            response.setResponse("account has been added");
            return response;
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

    @Override
    public Response<String> updateAccount(Account account) throws DatabaseErrorException {
        try {
            this.dao.update(account);
            Response<String> response = new Response<>();
            response.setSuccess(true);
            response.setResponse("account information updated");
            return response;
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

    @Override
    public Response<String> deleteAccount(String accountId) throws DatabaseErrorException {
        try {
            this.dao.delete(accountId);
            Response<String> response = new Response<>();
            response.setSuccess(true);
            response.setResponse("account deleted");
            return response;
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

    @Override
    public Response<List<Account>> findUserAccounts(String userId) throws DatabaseErrorException {
        try {
            List<Account> accounts = this.dao.findAll();
            List<Account> userAccounts = accounts.stream().filter(a -> a.getUserId().equals(userId)).toList();
            Response<List<Account>> response = new Response<>();
            response.setSuccess(true);
            response.setResponse(userAccounts);
            return response;
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }
}
