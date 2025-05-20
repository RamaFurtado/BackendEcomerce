package com.example.demo.controller;

import com.example.demo.model.Descuento;
import com.example.demo.services.DescuentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/descuentos")
@RequiredArgsConstructor
public class DescuentoController {
    private final DescuentoService descuentoService;

    @GetMapping("/activos")
    public List<Descuento> listarActivos() {
        return descuentoService.obtenerActivos();
    }

    @PostMapping
    public ResponseEntity<Descuento> crear(@RequestBody @Valid Descuento descuento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(descuentoService.crear(descuento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        descuentoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}