package com.example.demo.controller;

import com.example.demo.model.Detalle;
import com.example.demo.services.DetalleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles")
@RequiredArgsConstructor
public class DetalleController {
    private final DetalleService detalleService;

    @GetMapping("/producto/{productoId}")
    public List<Detalle> porProducto(@PathVariable Long productoId) {
        return detalleService.obtenerPorProducto(productoId);
    }

    @PostMapping
    public ResponseEntity<Detalle> crear(@RequestBody @Valid Detalle detalle) {
        return ResponseEntity.status(HttpStatus.CREATED).body(detalleService.crearDetalle(detalle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        detalleService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}