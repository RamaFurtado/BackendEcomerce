package com.example.demo.controller.generics;

import com.example.demo.services.generics.IGenericService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor

public abstract class GenericController<T, ID> {

    protected final IGenericService<T, ID> service;

    @PostMapping
    public ResponseEntity<T> crear(@RequestBody @Valid T entity) {
        return new ResponseEntity<>(service.crear(entity), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> obtenerPorId(@PathVariable ID id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<T>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> actualizar(@PathVariable ID id, @RequestBody @Valid T entity) {
        return ResponseEntity.ok(service.actualizar(id, entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable ID id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
