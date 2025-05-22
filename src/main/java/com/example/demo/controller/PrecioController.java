package com.example.demo.controller;

import com.example.demo.controller.generics.GenericController;
import com.example.demo.model.Precio;
import com.example.demo.services.PrecioService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/precios")
public class PrecioController extends GenericController<Precio, Long> {

    private final PrecioService precioService;

    public PrecioController(PrecioService precioService) {
        super(precioService);
        this.precioService = precioService;
    }

    // Endpoints específicos que no son CRUD básico:

    @GetMapping("/margen")
    public List<Precio> obtenerPorMargenMinimo(@RequestParam Double margen) {
        return precioService.buscarPorMargenMinimo(margen);
    }

    @GetMapping("/con-descuento")
    public List<Precio> obtenerConDescuento() {
        return precioService.buscarConDescuentosActivos();
    }
}
