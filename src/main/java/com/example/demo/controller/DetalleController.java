package com.example.demo.controller;

import com.example.demo.controller.generics.GenericController;
import com.example.demo.model.Detalle;
import com.example.demo.services.DetalleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles")

public class DetalleController extends GenericController<Detalle, Long> {

    private final DetalleService detalleService;

    public DetalleController(DetalleService detalleService) {
        super(detalleService);
        this.detalleService = detalleService;
    }

    @GetMapping("/producto/{productoId}")
    public List<Detalle> porProducto(@PathVariable Long productoId) {
        return detalleService.obtenerPorProducto(productoId);
    }

    @PostMapping
    public ResponseEntity<Detalle> crear(@RequestBody @Valid Detalle detalle) {
        return ResponseEntity.status(HttpStatus.CREATED).body(detalleService.crearDetalle(detalle));
    }
}
