package com.example.demo.services;

import com.example.demo.model.Categoria;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.services.generics.GenericServiceImpl;
import org.springframework.stereotype.Service;



@Service
public class CategoriaService extends GenericServiceImpl<Categoria, Long> {
    public CategoriaService(CategoriaRepository repository) {
        super(repository);
    }
}