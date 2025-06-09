package com.example.demo.controller;

import com.example.demo.dto.DetalleRequestDTO;
import com.example.demo.dto.ProductoCatalogoDTO;
import com.example.demo.dto.ProductoRequestDTO;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.services.CategoriaService;
import com.example.demo.services.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final PrecioRepository precioRepository;
    private final TallesRepository tallesRepository;
    private final DetalleRepository detalleRepository;
    private final ImagenRepository imagenRepository;
    private final DetalleImagenRepository detalleImagenRepository;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Producto> crearProducto(@RequestBody ProductoRequestDTO dto) {
        System.out.println("LLEGÓ AL CONTROLLER ");
        Producto producto = productoService.crearProducto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }




    //public ResponseEntity<Producto> crearProducto(@RequestBody ProductoRequestDTO dto) {
    //    System.out.println("LLEGÓ AL CONTROLLER ");
    //    return ResponseEntity.status(HttpStatus.CREATED).body(productoService.crearProducto(dto));
    //}


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
            @RequestParam(required = false) String talle,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax,
            @RequestParam(required = false) String sexo,
            @RequestParam(required = false) String tipoProducto
    ) {
        return ResponseEntity.ok(productoService.filtrarProductos(talle, marca, precioMin, precioMax, sexo, tipoProducto));
    }
    @GetMapping("/catalogo")
    public List<ProductoCatalogoDTO> obtenerCatalogo() {
        return productoService.obtenerCatalogo();
    }



    @PostMapping("/{productoId}/detalles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Detalle> agregarDetalle(
            @PathVariable Long productoId,
            @RequestBody DetalleRequestDTO dto) {

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Precio precio = new Precio();
        precio.setPrecioCompra(dto.getPrecioCompra());
        precio.setPrecioVenta(dto.getPrecioVenta());
        precio = precioRepository.save(precio);

        Talles talle = tallesRepository.findByTalle(dto.getTalle())
                .orElseGet(() -> {
                    Talles nuevo = new Talles();
                    nuevo.setTalle(dto.getTalle());
                    return tallesRepository.save(nuevo);
                });


        Detalle detalle = new Detalle();
        detalle.setProducto(producto);
        detalle.setTalle(talle);
        detalle.setPrecio(precio);
        detalle.setStock(dto.getStock());
        detalle.setColor(dto.getColor());
        detalle.setMarca(dto.getMarca());
        detalle.setEstado(dto.getEstado());

        detalle = detalleRepository.save(detalle);

        // Asociar imágenes
        for (String url : dto.getImagenesUrls()) {
            Imagen imagen = new Imagen();
            imagen.setUrl(url);
            imagen = imagenRepository.save(imagen);

            DetalleImagen detalleImagen = new DetalleImagen();
            detalleImagen.setDetalle(detalle);
            detalleImagen.setImagen(imagen);
            detalleImagenRepository.save(detalleImagen);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(detalle);
    }



    @PutMapping("/{id}/activar")
    public ResponseEntity<Producto> activarProducto(@PathVariable Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setActivo(true);
        return ResponseEntity.ok(productoRepository.save(producto));
    }
}