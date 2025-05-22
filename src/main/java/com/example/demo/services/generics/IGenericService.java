package com.example.demo.services.generics;

import java.util.List;

public interface IGenericService<T, ID> {
    T crear(T entity);
    T obtenerPorId(ID id);
    List<T> obtenerTodos();
    T actualizar(ID id, T entity);
    void eliminar(ID id);
}