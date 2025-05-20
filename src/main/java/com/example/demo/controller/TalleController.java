package com.example.demo.controller;

import com.example.demo.model.Talle;
import com.example.demo.services.TalleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@RestController
@RequestMapping("/api/talles")
@RequiredArgsConstructor
public class TalleController {

    private final TalleService talleService;

    @GetMapping
    public List<Talle> obtenerTodos() {
        return talleService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Talle> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(talleService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Talle> crear(@RequestBody @Valid Talle talle) {
        return ResponseEntity.status(HttpStatus.CREATED).body(talleService.crear(talle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        talleService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
