package com.example.demo.controller;

import com.example.demo.model.OrdenCompraDetalle;
import com.example.demo.services.OrdenCompraDetalleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes/detalles")
@RequiredArgsConstructor
public class OrdenCompraDetalleController {
    private final OrdenCompraDetalleService ordenCompraDetalleService;

    @GetMapping("/orden/{ordenId}")
    public List<OrdenCompraDetalle> porOrden(@PathVariable Long ordenId) {
        return ordenCompraDetalleService.obtenerPorOrden(ordenId);
    }

    @PostMapping
    public ResponseEntity<OrdenCompraDetalle> crear(@RequestBody @Valid OrdenCompraDetalle detalle) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ordenCompraDetalleService.crear(detalle));
    }
}
