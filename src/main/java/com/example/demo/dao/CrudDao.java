package com.example.demo.dao;

import java.util.List;

import com.example.demo.model.Account;

public interface CrudDao<T> {
    public void store(T data);
    public T findById(String id);
    public List<T> findAll();
}
