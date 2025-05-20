package com.example.demo.repository;

import com.example.demo.model.Detalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleRepository extends JpaRepository<Detalle, Long> {

    List<Detalle> findByProductoId(Long productoId);

    List<Detalle> findByTalleId(Long talleId);



    List<Detalle> findByProductoIdAndTalleId(Long productoId, Long talleId);


    List<Detalle> findByMarca(String marca);


    @Query("SELECT d FROM Detalle d WHERE d.producto.id = :productoId AND d.stock > 0")
    List<Detalle> findDisponiblesByProductoId(Long productoId);
}