package com.example.demo.controller;

import com.example.demo.controller.generics.GenericController;
import com.example.demo.model.Categoria;
import com.example.demo.services.CategoriaService;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/categorias")
public class CategoriaController extends GenericController<Categoria, Long> {
    public CategoriaController(CategoriaService service) {
        super(service);
    }
}
