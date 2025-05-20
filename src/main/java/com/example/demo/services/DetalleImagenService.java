package com.example.demo.services;

import com.example.demo.model.DetalleImagen;
import com.example.demo.model.Imagen;
import com.example.demo.repository.DetalleImagenRepository;
import com.example.demo.repository.DetalleRepository;
import com.example.demo.repository.ImagenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DetalleImagenService {

    private final DetalleImagenRepository detalleImagenRepository;
    private final DetalleRepository detalleRepository;
    private final ImagenRepository imagenRepository;

    public DetalleImagen crear(DetalleImagen detalleImagen) {
        // Valida la  existencia de detalle e imagen antes de guardar
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
}
