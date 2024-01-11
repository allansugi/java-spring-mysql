package com.example.demo.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.model.Account;

public interface CrudDao<T> {
    public void store(T data) throws SQLException;
    public T findById(String id) throws SQLException;
    public List<T> findAll() throws SQLException;
    public void delete(String id) throws SQLException;
}
