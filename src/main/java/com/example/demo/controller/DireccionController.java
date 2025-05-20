package com.example.demo.controller;

import com.example.demo.model.Direccion;
import com.example.demo.services.DireccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/direcciones")
@RequiredArgsConstructor
public class DireccionController {
    private final DireccionService direccionService;

    @GetMapping("/usuario/{usuarioId}")
    public List<Direccion> porUsuario(@PathVariable Long usuarioId) {
        return direccionService.obtenerPorUsuario(usuarioId);
    }

    @PostMapping
    public ResponseEntity<Direccion> crear(@RequestBody @Valid Direccion direccion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(direccionService.crear(direccion));
    }
}