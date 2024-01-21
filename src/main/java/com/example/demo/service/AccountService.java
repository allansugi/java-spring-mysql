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
    public Response<String> addAccount(AccountForm form) throws DatabaseErrorException;
    public Response<String> updateAccount(Account account) throws DatabaseErrorException;
    public Response<String> deleteAccount(String accountId) throws DatabaseErrorException;
    public Response<List<Account>> findUserAccounts(String userId) throws DatabaseErrorException, NoAccountFoundException;
}
