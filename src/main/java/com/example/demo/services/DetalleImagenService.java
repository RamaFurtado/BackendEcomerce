package com.example.demo.services;

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
        Optional<Producto> productoOptional = productoRepository.findById(productoId);
        Optional<Imagen> imagenOptional = imagenRepository.findById(imagenId);

        if (productoOptional.isEmpty() || imagenOptional.isEmpty()) return false;

        DetalleImagen detalle = new DetalleImagen();
        detalle.setProducto(productoOptional.get());
        detalle.setImagen(imagenOptional.get());
        detalleImagenRepository.save(detalle);

        return true;
    }

    public List<Imagen> obtenerImagenesPorProducto(Long productoId) {
        return detalleImagenRepository.findByProductoId(productoId)
                .stream()
                .map(DetalleImagen::getImagen)
                .collect(Collectors.toList());
    }
}
