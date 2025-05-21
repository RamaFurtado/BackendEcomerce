package com.example.demo.services;

import com.example.demo.dto.ProductoRequestDTO;
import com.example.demo.enums.Sexo;
import com.example.demo.enums.TipoProducto;
import com.example.demo.model.*;
import com.example.demo.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final TallesRepository tallesRepository;
    private final PrecioRepository precioRepository;
    private final DetalleRepository detalleRepository;
    private final ImagenRepository imagenRepository;
    private final DetalleImagenRepository detalleImagenRepository;

    public Producto crearProducto(ProductoRequestDTO dto) {



        Producto producto = new Producto();

        producto.setNombre(dto.getNombre());
        producto.setSexo(dto.getSexo());
        producto.setTipoProducto(dto.getTipoProducto());
        producto.setCategoria(dto.getCategoria());
        producto.setDetalles(new ArrayList<>());

        producto = productoRepository.save(producto);

        Precio precio = new Precio();
        precio.setPrecioCompra(dto.getPrecioCompra());
        precio.setPrecioVenta(dto.getPrecioVenta());
        precio = precioRepository.save(precio);

        Detalle detalle = new Detalle();
        detalle.setEstado(dto.getEstado());
        detalle.setMarca(dto.getMarca());
        detalle.setColor(dto.getColor());
        detalle.setStock(dto.getStock());
        detalle.setProducto(producto);
        detalle.setPrecio(precio);


        // Buscar talle por nombre o crearlo
        Talles talle = tallesRepository.findByTalle(dto.getTalle())
                .orElseGet(() -> {
                    Talles nuevoTalle = new Talles();
                    nuevoTalle.setTalle(dto.getTalle());
                    return tallesRepository.save(nuevoTalle);
                });

        detalle = detalleRepository.save(detalle);

        producto.getDetalles().add(detalle);

        for (String url : dto.getImagenesUrls()) {
            Imagen imagen = new Imagen();
            imagen.setUrl(url);
            imagen = imagenRepository.save(imagen);

            DetalleImagen detalleImagen = new DetalleImagen();
            detalleImagen.setDetalle(detalle);
            detalleImagen.setImagen(imagen);
            detalleImagenRepository.save(detalleImagen);
        }

        return productoRepository.save(producto);
    }
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }


    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }


    public void eliminar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productoRepository.delete(producto);
    }

}