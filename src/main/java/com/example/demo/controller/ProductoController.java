package com.example.demo.controller;

import com.example.demo.dto.ProductoCatalogoDTO;
import com.example.demo.dto.ProductoRequestDTO;
import com.example.demo.enums.Sexo;
import com.example.demo.enums.TipoProducto;
import com.example.demo.model.Producto;
import com.example.demo.services.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody @Valid ProductoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.crearProducto(dto));
    }

    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/filtrar")
    public ResponseEntity<List<Producto>> filtrarProductos(
            @RequestParam(required = false) List<String> talle,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax,
            @RequestParam(required = false) String sexo,
            @RequestParam(required = false) String tipoProducto,
            @RequestParam (required = false) List<String> colores,
            @RequestParam (required = false) String categoria
    ) {
        Sexo sexoEnum = null;
        TipoProducto tipoProductoEnum = null;

        try{
            if (sexo != null) sexoEnum = Sexo.valueOf(sexo.toUpperCase());
            if (tipoProducto != null) tipoProductoEnum = TipoProducto.valueOf(tipoProducto.toUpperCase());
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(productoService.filtrarProductos(talle, precioMin, precioMax, sexoEnum, tipoProductoEnum, colores, categoria));
    }
    @GetMapping("/catalogo")
    public List<ProductoCatalogoDTO> obtenerCatalogo() {
        return productoService.obtenerCatalogo();
    }



}