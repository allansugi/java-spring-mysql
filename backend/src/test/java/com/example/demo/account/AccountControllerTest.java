package com.example.demo.account;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.example.demo.controller.AccountController;
import com.example.demo.exception.NoAccountFoundException;
import com.example.demo.form.AccountForm;
import com.example.demo.model.Account;
import com.example.demo.response.Response;
import com.example.demo.service.AccountServiceImpl;
import com.example.demo.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();
    private final JWTUtil util = new JWTUtil();

    @MockBean
    private AccountServiceImpl service;

    private AccountForm newForm() {
        AccountForm form = new AccountForm();
        form.setAccount_name("GitHub");
        form.setAccount_username("user123");
        form.setAccount_password("new_Password123");
        return form;
    }

    private Account mockAccount(AccountForm form, String userId) {
        return new Account(userId, form);
    }

    @Test
    public void insertNewAccount_success_return_200() throws Exception {
        Response<String> response = new Response<>();
        response.setSuccess(true);
        response.setResponse("account added");

        AccountForm form = newForm();
        String id = UUID.randomUUID().toString();
        String token = util.createToken(id);

        Cookie cookie = new Cookie("token", token);

        when(service.addAccount(token, form)).thenReturn(response);

        this.mockMvc.perform(post("/api/account/add")
                    .cookie(cookie)
                    .content(mapper.writeValueAsString(form))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
    }

    @Test
    public void updateExistingAccount_success_return_200() throws Exception {
        Response<String> response = new Response<>();
        response.setSuccess(true);
        response.setResponse("account information updated");

        AccountForm form = newForm();
        String id = UUID.randomUUID().toString();
        String token = util.createToken(id);

        Account account = mockAccount(form, id);

        Cookie cookie = new Cookie("token", token);

        when(service.updateAccount(token, account)).thenReturn(response);

        this.mockMvc.perform(put("/api/account/update")
                    .cookie(cookie)
                    .content(mapper.writeValueAsString(account))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
    }

    @Test
    public void deleteAccount_success_return_200() throws Exception {
        Response<String> response = new Response<>();
        response.setSuccess(true);
        response.setResponse("account deleted");

        String userId = UUID.randomUUID().toString();
        String token = util.createToken(userId);
        Cookie cookie = new Cookie("token", token);

        String accountId = UUID.randomUUID().toString();

        when(service.deleteAccount(token, accountId)).thenReturn(response);

        this.mockMvc.perform(delete("/api/account/delete/{accountId}", accountId)
                    .cookie(cookie))
                    .andExpect(status().isOk());
    }

    @Test
    public void findUserAccounts_success_return_200() throws Exception {
        // assume there are accounts found
        List<Account> accounts = new ArrayList<>();
        Response<List<Account>> response = new Response<>();
        response.setSuccess(true);
        response.setResponse(accounts);

        String userId = UUID.randomUUID().toString();
        String token = util.createToken(userId);
        Cookie cookie = new Cookie("token", token);

        this.mockMvc.perform(get("/api/account/find/accounts")
                    .cookie(cookie))
                    .andExpect(status().isOk());
    }
    
    @Test
    public void findUserAccounts_fails_return_404() throws Exception {
        // assume there are accounts found
        List<Account> accounts = new ArrayList<>();
        Response<List<Account>> response = new Response<>();
        response.setSuccess(true);
        response.setResponse(accounts);

        String userId = UUID.randomUUID().toString();
        String token = util.createToken(userId);
        Cookie cookie = new Cookie("token", token);

        doThrow(new NoAccountFoundException()).when(service).findUserAccounts(token);

        this.mockMvc.perform(get("/api/account/find/accounts")
                    .cookie(cookie))
                    .andExpect(status().isNotFound());
    }
}
