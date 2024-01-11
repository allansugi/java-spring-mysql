package com.example.demo.dao;

import java.sql.SQLException;
import java.util.List;

import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.model.Account;

public interface CrudDao<T> {
    public void store(T data) throws SQLException;
    public T findById(String id);
    public List<T> findAll() throws SQLException;
}
