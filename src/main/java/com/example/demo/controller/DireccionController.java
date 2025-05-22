package com.example.demo.controller;

import com.example.demo.controller.generics.GenericController;
import com.example.demo.model.Direccion;
import com.example.demo.services.DireccionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/direcciones")
public class DireccionController extends GenericController<Direccion, Long> {

    private final DireccionService direccionService;

    public DireccionController(DireccionService direccionService) {
        super(direccionService);
        this.direccionService = direccionService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Direccion> porUsuario(@PathVariable Long usuarioId) {
        return direccionService.obtenerPorUsuario(usuarioId);
    }

    @PostMapping
    public ResponseEntity<Direccion> crear(@RequestBody @Valid Direccion direccion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(direccionService.crear(direccion));
    }
}
