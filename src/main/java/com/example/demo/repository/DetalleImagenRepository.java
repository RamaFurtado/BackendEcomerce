package com.example.demo.repository;

import com.example.demo.model.DetalleImagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleImagenRepository extends JpaRepository<DetalleImagen, Long> {

    List<DetalleImagen> findByDetalleId(Long detalleId);

    List<DetalleImagen> findByProductoId(Long productoId);

    List<DetalleImagen> findByImagenId(Long imagenId);

    void deleteByDetalleId(Long detalleId);

    void deleteByImagenId(Long imagenId);

    boolean existsByDetalleIdAndImagenId(Long detalleId, Long imagenId);
}