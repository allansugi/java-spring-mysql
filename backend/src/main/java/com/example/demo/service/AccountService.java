package com.example.demo.service;

import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.exception.NoAccountFoundException;
import com.example.demo.form.AccountForm;
import com.example.demo.model.Account;
import com.example.demo.response.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {
    public Response<String> addAccount(String token, AccountForm form) throws DatabaseErrorException;

    public Response<String> updateAccount(String token, Account account) throws DatabaseErrorException;
    public Response<String> deleteAccount(String token, String accountId) throws DatabaseErrorException;

    Response<List<Account>> findUserAccounts(String token) throws DatabaseErrorException, NoAccountFoundException;
}
