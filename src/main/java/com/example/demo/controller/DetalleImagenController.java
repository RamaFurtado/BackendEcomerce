package com.example.demo.controller;

import com.example.demo.controller.generics.GenericController;
import com.example.demo.model.DetalleImagen;
import com.example.demo.model.Imagen;
import com.example.demo.services.DetalleImagenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-imagenes")
public class DetalleImagenController extends GenericController<DetalleImagen, Long> {

    private final DetalleImagenService detalleImagenService;

    public DetalleImagenController(DetalleImagenService detalleImagenService) {
        super(detalleImagenService);
        this.detalleImagenService = detalleImagenService;
    }

    @GetMapping("/detalle/{detalleId}")
    public List<Imagen> listarImagenesPorDetalle(@PathVariable Long detalleId) {
        return detalleImagenService.obtenerImagenesPorDetalle(detalleId);
    }

    @PostMapping
    public ResponseEntity<DetalleImagen> crear(@RequestBody @Valid DetalleImagen detalleImagen) {
        return ResponseEntity.status(HttpStatus.CREATED).body(detalleImagenService.crear(detalleImagen));
    }

    @PostMapping("/asociar")
    public ResponseEntity<String> asociarImagenProducto(
            @RequestParam Long productId,
            @RequestParam Long imagenId
    ) {
        return detalleImagenService.asociarImagenConProducto(productId,imagenId)
                ? ResponseEntity.ok("Imagen asociada correctamente al producto")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede asociar");
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<Imagen>> obtenerImagenesPorProducto(@PathVariable Long productoId) {
        return ResponseEntity.ok(detalleImagenService.obtenerImagenesPorProducto(productoId));
    }
}
