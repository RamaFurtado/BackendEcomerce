package com.example.demo.services;

import com.example.demo.repositories.BaseRepository;
import java.util.List;
import java.util.Optional;

public class BaseServiceImpl<T, ID> implements BaseService<T, ID> {

    protected BaseRepository<T, ID> repository;

    public BaseServiceImpl(BaseRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public T findById(ID id) {
        Optional<T> result = repository.findById(id);
        return result.orElse(null);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T update(ID id, T entity) {
        if (!repository.existsById(id)) {
            return null;
        }
        return repository.save(entity);
    }

    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }
}
