package com.example.demo.controller;

import com.example.demo.controller.generics.GenericController;
import com.example.demo.model.Descuento;
import com.example.demo.services.DescuentoService;



import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/descuentos")
public class DescuentoController extends GenericController<Descuento, Long> {

    private final DescuentoService descuentoService;

    // Constructor para inyectar el service base en el genérico
    public DescuentoController(DescuentoService descuentoService) {
        super(descuentoService);
        this.descuentoService = descuentoService;
    }

    @GetMapping("/activos")
    public List<Descuento> listarActivos() {
        return descuentoService.obtenerActivos();
    }
}
