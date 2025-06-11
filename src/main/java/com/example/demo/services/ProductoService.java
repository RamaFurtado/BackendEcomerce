package com.example.demo.services;


import com.example.demo.dto.ProductoCatalogoDTO;
import com.example.demo.dto.ProductoRequestDTO;
import  com.example.demo.enums.Sexo;
import  com.example.demo.enums.TipoProducto;

import com.example.demo.model.*;
import com.example.demo.repository.*;

import com.example.demo.services.generics.GenericServiceImpl;

import jakarta.transaction.Transactional;
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


    public List<Producto> filtrarProductos(List<String> talle, Double precioMin,
                                           Double precioMax, Sexo sexo, TipoProducto tipoProducto, List<String> colores, String categoriaNombre) {
        Categoria categoria = null;
        if (categoriaNombre != null && !categoriaNombre.isBlank()) {
            categoria = categoriaRepository.findByNombreIgnoreCase(categoriaNombre).orElse(null);
        }

        return productoRepository.filtrarProductos(talle, precioMin, precioMax, sexo, tipoProducto,colores,categoria);
    }

    public Producto crearProducto(ProductoRequestDTO dto) {

        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setSexo(dto.getSexo());
        producto.setTipoProducto(dto.getTipoProducto());
        producto.setActivo(false);


        Optional<Categoria> categoriaOpt = categoriaRepository.findByNombre(dto.getCategoriaNombre());
        Categoria categoria = categoriaOpt.orElseGet(() -> {
            Categoria nueva = new Categoria();
            nueva.setNombre(dto.getCategoriaNombre());
            return categoriaRepository.save(nueva);
        });




        producto.setCategoria(categoria);
        producto.setDetalles(new ArrayList<>());


        return productoRepository.save(producto);
    }


    public List<ProductoCatalogoDTO> obtenerCatalogo() {
        List<Producto> productos = productoRepository.findAll();

        return productos.stream().map(producto -> {


            if (producto.getTipoProducto() != null) {
                System.out.println("TipoProducto.name(): " + producto.getTipoProducto().name());
                System.out.println("TipoProducto.toString(): " + producto.getTipoProducto().toString());
            }

            Detalle detalle = producto.getDetalles().stream().findFirst().orElse(null);
            String marca = detalle != null ? detalle.getMarca() : "";
            String color = detalle != null ? detalle.getColor() : "";
            Double precio = detalle != null && detalle.getPrecio() != null
                    ? detalle.getPrecio().getPrecioVenta() : 0.0;

            String imagenUrl = (producto.getImagen() != null) ? producto.getImagen().getUrl() : "";


            String tipoProductoStr = producto.getTipoProducto() != null
                    ? producto.getTipoProducto().name()
                    : "VALOR_NULO";



            ProductoCatalogoDTO dto = new ProductoCatalogoDTO(
                    producto.getId(),
                    producto.getNombre(),
                    marca,
                    color,
                    precio,
                    producto.getCategoria() != null ? producto.getCategoria().getNombre() : "",
                    imagenUrl,
                    tipoProductoStr
            );



            return dto;
        }).toList();
    }


    @Transactional
    public void eliminar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));

        //elimina relaciones detalleImagen y precio
        for (Detalle detalle : producto.getDetalles()) {
            detalleImagenRepository.deleteAllByDetalle(detalle);

            // Eliminar precio si está presente
            if (detalle.getPrecio() != null) {
                precioRepository.delete(detalle.getPrecio());
            }
        }

        //elimina los detalles directamente
        detalleRepository.deleteAll(producto.getDetalles());

        //limpia relaciones en memoria
        producto.getDetalles().clear();

        //desvincula imagen antes de borrar el producto
        Imagen imagen = producto.getImagen();
        producto.setImagen(null);

        //guardas sin referencias que generen conflicto
        productoRepository.save(producto);

        //verifica si la imagen se puede borrar
        if(imagen != null && !productoRepository.existsByImagen(imagen)) {
            imagenRepository.delete(imagen);
        }

        //elimina el producto
        productoRepository.delete(producto);
    }
    public Producto actualizarProducto(Long id, ProductoRequestDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setNombre(dto.getNombre());
        producto.setSexo(dto.getSexo());
        producto.setTipoProducto(dto.getTipoProducto());

        Optional<Categoria> categoriaOpt = categoriaRepository.findByNombre(dto.getCategoriaNombre());
        Categoria categoria = categoriaOpt.orElseGet(() -> {
            Categoria nueva = new Categoria();
            nueva.setNombre(dto.getCategoriaNombre());
            return categoriaRepository.save(nueva);
        });

        producto.setCategoria(categoria);

        return productoRepository.save(producto);
    }

}
