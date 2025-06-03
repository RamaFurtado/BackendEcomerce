package com.example.demo.controller;

import com.example.demo.controller.generics.GenericController;
import com.example.demo.model.Imagen;

import com.example.demo.model.Producto;
import com.example.demo.services.ImagenService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/imagenes")
public class ImagenController extends GenericController<Imagen, Long> {

    private final ImagenService imagenService;

    public ImagenController(ImagenService imagenService) {
        super(imagenService);
        this.imagenService = imagenService;
    }


    @PostMapping
    public ResponseEntity<Imagen> crear(@RequestBody @Valid Imagen imagen) {
        return ResponseEntity.status(HttpStatus.CREATED).body(imagenService.crear(imagen));
    }

    @GetMapping("/dto")
    public List<Imagen> listarImagenes() {return imagenService.obtenerTodas();}


    @PostMapping("/upload")
    public ResponseEntity<Imagen> subirImagen(@RequestParam("file") MultipartFile archivo) {
        try {
            Imagen imagen = imagenService.subirYGuardarImagen(archivo);
            return ResponseEntity.status(HttpStatus.CREATED).body(imagen);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

