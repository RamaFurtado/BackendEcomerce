package com.example.demo.services;

import com.example.demo.dto.ProductoCatalogoDTO;
import com.example.demo.dto.ProductoRequestDTO;

import com.example.demo.enums.Sexo;
import com.example.demo.enums.TipoProducto;
import com.example.demo.model.*;
import com.example.demo.repository.*;

import com.example.demo.services.generics.GenericServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Producto crearProducto(ProductoRequestDTO dto) {
        Producto producto = new Producto();

        producto.setNombre(dto.getNombre());
        producto.setSexo(dto.getSexo());
        producto.setTipoProducto(dto.getTipoProducto());

        // Buscar categoría por nombre o crearla si no existe
        Categoria categoria = categoriaRepository.findByNombre(dto.getCategoriaNombre())
                .orElseGet(() -> {
                    Categoria nuevaCategoria = new Categoria();
                    nuevaCategoria.setNombre(dto.getCategoriaNombre());
                    return categoriaRepository.save(nuevaCategoria);
                });
        producto.setCategoria(categoria);

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
        detalle.setTalle(talle);

        detalle = detalleRepository.save(detalle);
        detalleRepository.flush();

        producto.getDetalles().add(detalle);

    // Eliminar relaciones anteriores (si existiesen)
        detalleImagenRepository.deleteAllByDetalle(detalle);

    // Asociar nuevas imágenes y guardar la mas reciente
        Imagen imagenMasReciente = null;

        for (String url : dto.getImagenesUrls()) {
            Imagen imagen = new Imagen();
            imagen.setUrl(url);
            imagen = imagenRepository.save(imagen);

            DetalleImagen detalleImagen = new DetalleImagen();
            detalleImagen.setDetalle(detalle);
            detalleImagen.setImagen(imagen);
            detalleImagen.setProducto(producto);
            detalleImagen = detalleImagenRepository.save(detalleImagen);

            System.out.println("DEBUG: fechaCreacion = " + detalleImagen.getFechaCreacion());

            imagenMasReciente = imagen; // tomamos la última como la más reciente
        }

        // Establecer la imagen más reciente como principal
        if (imagenMasReciente != null) {
            producto.setImagen(imagenMasReciente);
        }

        return productoRepository.save(producto);
    }


    public List<Producto> filtrarProductos(List<String> talle, Double precioMin,
                                           Double precioMax, Sexo sexo, TipoProducto tipoProducto, List<String> colores, String categoriaNombre) {
        Categoria categoria = null;
        if (categoriaNombre != null && !categoriaNombre.isBlank()) {
            categoria = categoriaRepository.findByNombreIgnoreCase(categoriaNombre).orElse(null);
        }

        return productoRepository.filtrarProductos(talle, precioMin, precioMax, sexo, tipoProducto,colores,categoria);
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
                DetalleImagen detalleImagen = detalle.getImagenes().stream()
                .sorted(Comparator.comparing(
                        DetalleImagen::getFechaCreacion,
                        Comparator.nullsLast(Comparator.reverseOrder())
                ))
                        .findFirst()
                        .orElse(null);
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
                    imagenUrl
            );
        }).toList();
    }

    //eliminar producto completo
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
}
