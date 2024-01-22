package com.example.demo.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dao.AccountDaoImpl;
import com.example.demo.exception.NoAccountFoundException;
import com.example.demo.form.AccountForm;
import com.example.demo.model.Account;
import com.example.demo.response.Response;
import com.example.demo.service.AccountServiceImpl;
import com.example.demo.util.JWTUtil;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @InjectMocks
    private AccountServiceImpl service;
    @Mock
    private AccountDaoImpl dao;
    @Mock
    private JWTUtil util;

    /**
     * generic response class
     * note: Response is this context is a custom class in response folder
     * @param <T>
     * @param result
     * @return
     */
    private <T> Response<T> createSuccessResponse(T result) {
        Response<T> response = new Response<>();
        response.setSuccess(true);
        response.setResponse(result);
        return response;
    }

    /**
     * create new valid account form
     * @return account form
     */
    private AccountForm newAccountForm() {
        AccountForm form = new AccountForm();
        form.setAccount_name("GitHub");
        form.setAccount_username("user123");
        form.setAccount_password("new_Password123");
        return form;
    }

    /**
     * convert account form to account
     * @param form
     * @param userId
     * @return
     */
    private Account mockAccount(AccountForm form, String userId) {
        return new Account(userId, form);
    }

    private List<Account> mockListAccounts(int size, String userId) {
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Account account = new Account();
            account.setAccount_name("account" + size);
            account.setAccount_username("user" + size);
            account.setId(UUID.randomUUID().toString());
            account.setUserId(userId);
            account.setAccount_password("Password_123");
            accounts.add(account);
        }
        return accounts;
    }

    @Test
    public void addAccount_response_success() throws Exception {
        Response<String> response = createSuccessResponse("account added");
        String userId = UUID.randomUUID().toString();
        String token = util.createToken(userId);

        AccountForm form = newAccountForm();

        Response<String> testResponse = service.addAccount(token, form);

        assertEquals(testResponse.getSuccess(), response.getSuccess());
        assertEquals(testResponse.getResponse(), response.getResponse());
    }

    @Test
    public void updateAccount_response_success() throws Exception {
        Response<String> response = createSuccessResponse("account information updated");
        String userId = UUID.randomUUID().toString();
        String token = util.createToken(userId);

        AccountForm form = newAccountForm();
        Account account = mockAccount(form, userId);

        Response<String> testResponse = service.updateAccount(token, account);

        assertEquals(testResponse.getSuccess(), response.getSuccess());
        assertEquals(testResponse.getResponse(), response.getResponse());
    }

    @Test
    public void deleteAccount_response_success() throws Exception {
        Response<String> response = createSuccessResponse("account deleted");

        String accountId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        String token = util.createToken(userId);

        Response<String> testResponse = service.deleteAccount(token, accountId);

        assertEquals(testResponse.getSuccess(), response.getSuccess());
        assertEquals(testResponse.getResponse(), response.getResponse());
    }

    @Test
    public void findUserAccounts_response_success() throws Exception {
        String userId = UUID.randomUUID().toString();
        List<Account> accounts = mockListAccounts(4, userId);
        String token = util.createToken(userId);

        when(dao.findAll()).thenReturn(accounts);
        when(util.verifyToken(token)).thenReturn(userId);

        Response<List<Account>> response = createSuccessResponse(accounts);
        Response<List<Account>> testResponse = service.findUserAccounts(token);

        assertEquals(testResponse.getSuccess(), response.getSuccess());
        assertEquals(testResponse.getResponse(), response.getResponse());
    }

    @Test
    public void findUserAccounts_response_empty() throws Exception {
        String userId = UUID.randomUUID().toString();
        String anotherUserId = UUID.randomUUID().toString();
        List<Account> accounts = mockListAccounts(4, anotherUserId);
        String token = util.createToken(userId);

        when(dao.findAll()).thenReturn(accounts);
        when(util.verifyToken(token)).thenReturn(userId);

        assertThrows(NoAccountFoundException.class, () -> service.findUserAccounts(token));
    }
}
