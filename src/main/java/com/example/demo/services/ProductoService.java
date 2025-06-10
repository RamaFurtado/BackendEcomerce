package com.example.demo.services;

import com.example.demo.dto.DetalleRequestDTO;
import com.example.demo.dto.ProductoCatalogoDTO;
import com.example.demo.dto.ProductoRequestDTO;

import com.example.demo.model.*;
import com.example.demo.repository.*;

import com.example.demo.services.generics.GenericServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service

public class ProductoService extends GenericServiceImpl<Producto, Long> {

    private final ProductoRepository productoRepository;
    private final TallesRepository tallesRepository;
    private final PrecioRepository precioRepository;
    private final DetalleRepository detalleRepository;
    private final ImagenRepository imagenRepository;
    private final DetalleImagenRepository detalleImagenRepository;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public ProductoService(
            ProductoRepository productoRepository,
            TallesRepository tallesRepository,
            PrecioRepository precioRepository,
            DetalleRepository detalleRepository,
            ImagenRepository imagenRepository,
            DetalleImagenRepository detalleImagenRepository,
            CategoriaRepository categoriaRepository) {
        super(productoRepository);
        this.productoRepository = productoRepository;
        this.tallesRepository = tallesRepository;
        this.precioRepository = precioRepository;
        this.detalleRepository = detalleRepository;
        this.imagenRepository = imagenRepository;
        this.detalleImagenRepository = detalleImagenRepository;
        this.categoriaRepository = categoriaRepository;
    }


    public List<Producto> filtrarProductos(String talle, String marca, Double precioMin,
                                           Double precioMax, String sexo, String tipoProducto) {
        return productoRepository.filtrarProductos(talle, marca, precioMin, precioMax, sexo, tipoProducto);
    }

    public Producto crearProducto(ProductoRequestDTO dto) {
        System.out.println("LLEGÓ AL SERVICE ");
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setSexo(dto.getSexo());
        producto.setTipoProducto(dto.getTipoProducto());
        producto.setActivo(false); // lo dejás inactivo al crear

        // buscar o crear categoría
        Optional<Categoria> categoriaOpt = categoriaRepository.findByNombre(dto.getCategoriaNombre());
        Categoria categoria = categoriaOpt.orElseGet(() -> {
            Categoria nueva = new Categoria();
            nueva.setNombre(dto.getCategoriaNombre());
            return categoriaRepository.save(nueva);
        });


        System.out.println("PASO EL CATEGORIA FIND");

        producto.setCategoria(categoria);
        producto.setDetalles(new ArrayList<>());

        // guardar producto sin detalles aún
        return productoRepository.save(producto);
    }


    public List<ProductoCatalogoDTO> obtenerCatalogo() {
        List<Producto> productos = productoRepository.findAll();

        return productos.stream().map(producto -> {

            Detalle detalle = producto.getDetalles().stream().findFirst().orElse(null);
            String marca = detalle != null ? detalle.getMarca() : "";
            String color = detalle != null ? detalle.getColor() : "";
            Double precio = detalle != null && detalle.getPrecio() != null
                    ? detalle.getPrecio().getPrecioVenta() : 0.0;


            String imagenUrl = "";
            if (detalle != null && detalle.getImagenes() != null && !detalle.getImagenes().isEmpty()) {
                DetalleImagen detalleImagen = detalle.getImagenes().get(0);
                if (detalleImagen != null && detalleImagen.getImagen() != null) {
                    imagenUrl = detalleImagen.getImagen().getUrl();
                }
            }

            return new ProductoCatalogoDTO(
                    producto.getId(),
                    producto.getNombre(),
                    marca,
                    color,
                    precio,
                    producto.getCategoria() != null ? producto.getCategoria().getNombre() : "",
                    imagenUrl,
                    producto.getTipoProducto()
            );
        }).toList();
    }

}
