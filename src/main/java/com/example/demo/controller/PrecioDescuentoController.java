package com.example.demo.controller;

import com.example.demo.dto.PrecioDescuentoDTO;
import com.example.demo.model.PrecioDescuento;
import com.example.demo.services.PrecioDescuentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/precios-descuentos")
@RequiredArgsConstructor
public class PrecioDescuentoController {

    private final PrecioDescuentoService precioDescuentoService;

    @GetMapping("/activos")
    public List<PrecioDescuento> obtenerActivos(@RequestParam Date fecha) {
        return precioDescuentoService.obtenerDescuentosActivos(fecha); // nombre correcto
    }

    @PostMapping
    public ResponseEntity<PrecioDescuento> crearRelacion(@RequestBody PrecioDescuentoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(precioDescuentoService.crearRelacion(dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> eliminarRelacion(@RequestParam Long precioId, @RequestParam Long descuentoId) {
        precioDescuentoService.eliminarRelacion(precioId, descuentoId);
        return ResponseEntity.noContent().build();
    }
}
