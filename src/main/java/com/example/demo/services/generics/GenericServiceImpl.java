package com.example.demo.services.generics;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public abstract class GenericServiceImpl<T, ID> implements IGenericService<T, ID> {

    protected JpaRepository<T, ID> repository;

    @Override
    public T crear(T entity) {
        return repository.save(entity);
    }

    @Override
    public T obtenerPorId(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entidad no encontrada"));
    }

    @Override
    public List<T> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public T actualizar(ID id, T entity) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Entidad no encontrada para actualizar");
        }
        return repository.save(entity);
    }

    @Override
    public void eliminar(ID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Entidad no encontrada para eliminar");
        }
        repository.deleteById(id);
    }
}
