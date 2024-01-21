package com.example.demo.service;

import com.example.demo.dao.AccountDaoImpl;
import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.exception.NoAccountFoundException;
import com.example.demo.form.AccountForm;
import com.example.demo.model.Account;
import com.example.demo.response.Response;
import com.example.demo.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountDaoImpl dao;
    private final JWTUtil util;

    @Autowired
    public AccountServiceImpl(AccountDaoImpl dao, JWTUtil util) {
        this.dao = dao;
        this.util = util;
    }
    @Override
    public Response<String> addAccount(String token, AccountForm form) throws DatabaseErrorException {
        try {
            String userId = util.verifyToken(token);
            Account account = new Account(userId, form);
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
    public Response<String> updateAccount(String token, Account account) throws DatabaseErrorException {
        try {
            String userId = util.verifyToken(token);
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
    public Response<String> deleteAccount(String token, String accountId) throws DatabaseErrorException {
        try {
            String userId = util.verifyToken(token);
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
    public Response<List<Account>> findUserAccounts(String token) throws DatabaseErrorException, NoAccountFoundException {
        try {
            String userId = util.verifyToken(token);
            List<Account> accounts = this.dao.findAll();
            List<Account> userAccounts = accounts.stream().filter(a -> a.getUserId().equals(userId)).toList();

            if (userAccounts.isEmpty()) {
                throw new NoAccountFoundException("no accounts found for that user");
            }

            Response<List<Account>> response = new Response<>();
            response.setSuccess(true);
            response.setResponse(userAccounts);

            return response;
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }
}
