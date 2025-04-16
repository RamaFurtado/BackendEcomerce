package com.example.demo.controllers;

import java.util.List;

public interface BaseController<T> {
    List<T> findAll();
    T findById(Long id);
    T save(T entity);
    T update(Long id, T entity);
    void delete(Long id);
}
