package com.example.demo.controller;

import com.example.demo.controller.generics.GenericController;
import com.example.demo.dto.PrecioDescuentoDTO;
import com.example.demo.model.PrecioDescuento;
import com.example.demo.services.PrecioDescuentoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/precios-descuentos")
public class PrecioDescuentoController extends GenericController<PrecioDescuento, Long> {

    private final PrecioDescuentoService precioDescuentoService;

    public PrecioDescuentoController(PrecioDescuentoService service) {
        super(service);
        this.precioDescuentoService = service;
    }

    @GetMapping("/activos")
    public List<PrecioDescuento> obtenerActivos(@RequestParam Date fecha) {
        return precioDescuentoService.obtenerDescuentosActivos(fecha);
    }

    @PostMapping("/relacion")
    public ResponseEntity<PrecioDescuento> crearRelacion(@RequestBody PrecioDescuentoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(precioDescuentoService.crearRelacion(dto));
    }

    @DeleteMapping("/relacion")
    public ResponseEntity<Void> eliminarRelacion(@RequestParam Long precioId, @RequestParam Long descuentoId) {
        precioDescuentoService.eliminarRelacion(precioId, descuentoId);
        return ResponseEntity.noContent().build();
    }
}
