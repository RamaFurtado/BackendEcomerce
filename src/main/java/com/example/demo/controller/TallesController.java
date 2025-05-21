package com.example.demo.controller;

import com.example.demo.model.Talles;
import com.example.demo.services.TallesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@RestController
@RequestMapping("/api/talles")
@RequiredArgsConstructor
public class TallesController {

    private final TallesService tallesService;

    @GetMapping
    public List<Talles> obtenerTodos() {
        return tallesService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Talles> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tallesService.obtenerPorId(id));
    }



    @PostMapping
    public ResponseEntity<Talles> crear(@RequestBody @Valid Talles talle) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tallesService.crear(talle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tallesService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
