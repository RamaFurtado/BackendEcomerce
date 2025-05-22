package com.example.demo.controller;
import com.example.demo.dto.UsuarioDireccionDTO;
import com.example.demo.model.UsuarioDireccion;
import com.example.demo.services.UsuarioDireccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios-direcciones")
@RequiredArgsConstructor
public class UsuarioDireccionController {

    private final UsuarioDireccionService usuarioDireccionService;

    @GetMapping("/usuario/{usuarioId}")
    public List<UsuarioDireccion> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return usuarioDireccionService.obtenerPorUsuario(usuarioId);
    }

    @PostMapping
    public ResponseEntity<UsuarioDireccion> vincular(@RequestBody UsuarioDireccionDTO dto) {
        UsuarioDireccion creado = usuarioDireccionService.crearRelacion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @DeleteMapping
    public ResponseEntity<Void> eliminarVinculo(@RequestParam Long usuarioId, @RequestParam Long direccionId) {
        usuarioDireccionService.eliminarRelacion(usuarioId, direccionId);
        return ResponseEntity.noContent().build();
    }
}
