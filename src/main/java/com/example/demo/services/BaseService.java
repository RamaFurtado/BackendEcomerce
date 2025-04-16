package com.example.demo.services;

import java.util.List;

public interface BaseService<T, ID> {
    T save(T entity);
    T findById(ID id);
    List<T> findAll();
    T update(ID id, T entity);
    void delete(ID id);
}
