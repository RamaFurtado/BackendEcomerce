package com.example.demo.controller;

import com.example.demo.model.Precio;
import com.example.demo.services.PrecioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/precios")
@RequiredArgsConstructor
public class PrecioController {

    private final PrecioService precioService;

    @GetMapping("/margen")
    public List<Precio> obtenerPorMargenMinimo(@RequestParam Double margen) {
        return precioService.buscarPorMargenMinimo(margen); // cambiado
    }

    @GetMapping("/con-descuento")
    public List<Precio> obtenerConDescuento() {
        return precioService.buscarConDescuentosActivos(); // cambiado
    }
}