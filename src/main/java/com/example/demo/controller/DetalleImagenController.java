package com.example.demo.controller;

import com.example.demo.model.DetalleImagen;
import com.example.demo.model.Imagen;
import com.example.demo.services.DetalleImagenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-imagenes")
@RequiredArgsConstructor
public class DetalleImagenController {
    private final DetalleImagenService detalleImagenService;

    @GetMapping("/detalle/{detalleId}")
    public List<Imagen> listarImagenesPorDetalle(@PathVariable Long detalleId) {
        return detalleImagenService.obtenerImagenesPorDetalle(detalleId);
    }

    @PostMapping
    public ResponseEntity<DetalleImagen> crear(@RequestBody @Valid DetalleImagen detalleImagen) {
        return ResponseEntity.status(HttpStatus.CREATED).body(detalleImagenService.crear(detalleImagen));
    }
}