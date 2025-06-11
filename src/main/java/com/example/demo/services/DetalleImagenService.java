package com.example.demo.services;

import com.example.demo.model.Detalle;
import com.example.demo.model.DetalleImagen;
import com.example.demo.model.Imagen;
import com.example.demo.model.Producto;
import com.example.demo.repository.DetalleImagenRepository;
import com.example.demo.repository.DetalleRepository;
import com.example.demo.repository.ImagenRepository;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.services.generics.GenericServiceImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DetalleImagenService extends GenericServiceImpl<DetalleImagen, Long> {

    private final DetalleImagenRepository detalleImagenRepository;
    private final ProductoRepository productoRepository;
    private final DetalleRepository detalleRepository;
    private final ImagenRepository imagenRepository;

    public DetalleImagen crear(DetalleImagen detalleImagen) {
        if (!detalleRepository.existsById(detalleImagen.getDetalle().getId())) {
            throw new RuntimeException("Detalle no encontrado");
        }
        if (!imagenRepository.existsById(detalleImagen.getImagen().getId())) {
            throw new RuntimeException("Imagen no encontrada");
        }
        if (detalleImagenRepository.existsByDetalleIdAndImagenId(
                detalleImagen.getDetalle().getId(), detalleImagen.getImagen().getId())) {
            throw new RuntimeException("Ya existe una relación entre ese detalle y esa imagen");
        }
        return detalleImagenRepository.save(detalleImagen);
    }

    public List<Imagen> obtenerImagenesPorDetalle(Long detalleId) {
        List<DetalleImagen> relaciones = detalleImagenRepository.findByDetalleId(detalleId);
        return relaciones.stream()
                .map(DetalleImagen::getImagen)
                .collect(Collectors.toList());
    }

    public void eliminarPorDetalle(Long detalleId) {
        detalleImagenRepository.deleteByDetalleId(detalleId);
    }

    public void eliminarPorImagen(Long imagenId) {
        detalleImagenRepository.deleteByImagenId(imagenId);
    }

    public boolean asociarImagenConProducto(Long imagenId, Long productoId) {
        try {
            Optional<Producto> productoOptional = productoRepository.findById(productoId);
            Optional<Imagen> imagenOptional = imagenRepository.findById(imagenId);

            if (productoOptional.isEmpty()) {
                System.out.println("Producto no encontrado con ID: " + productoId);
                return false;
            }

            if (imagenOptional.isEmpty()) {
                System.out.println("Imagen no encontrada con ID: " + imagenId);
                return false;
            }

            Producto producto = productoOptional.get();
            Imagen imagen = imagenOptional.get();

            // Obtener primer detalle del producto
            List<Detalle> detalles = detalleRepository.findByProductoId(productoId);
            if (detalles.isEmpty()) {
                System.out.println("No hay detalles asociados al producto");
                return false;
            }

            Detalle detalle = detalles.get(0);
            System.out.println("Asociando imagen ID " + imagenId + " al detalle ID " + detalle.getId() + " del producto ID " + productoId);

            //Asociar la imagen nueva

            DetalleImagen detalleImagen = new DetalleImagen();
            detalleImagen.setProducto(producto);
            detalleImagen.setDetalle(detalle);
            detalleImagen.setImagen(imagen);
            detalleImagenRepository.save(detalleImagen);

            //Establecer la imagen mas nueva como principal
            List<DetalleImagen> imagenes = detalleImagenRepository.findByProductoId(productoId);
            imagenes.sort((a, b) -> b.getFechaCreacion().compareTo(a.getFechaCreacion())); // Orden descendente
            if (!imagenes.isEmpty()) {
                producto.setImagen(imagenes.get(0).getImagen());
                productoRepository.save(producto);
                System.out.println("Imagen principal del producto actualizada con la más reciente.");
            }

            return true;


        } catch (Exception e) {
            System.out.println("Error al asociar imagen con producto: " + e.getMessage());
            return false;
        }
    }


    public List<Imagen> obtenerImagenesPorProducto(Long productoId) {
        return detalleImagenRepository.findByProductoId(productoId)
                .stream()
                .map(DetalleImagen::getImagen)
                .collect(Collectors.toList());
    }


    public boolean asignarPrincipal(Long productoId, Long imagenId) {
        Optional<Producto> productoOpt = productoRepository.findById(productoId);
        Optional<Imagen> imagenOpt = imagenRepository.findById(imagenId);

        if (productoOpt.isEmpty() || imagenOpt.isEmpty()) return false;

        Producto producto = productoOpt.get();
        producto.setImagen(imagenOpt.get());

        productoRepository.save(producto);
        return true;
    }

}
