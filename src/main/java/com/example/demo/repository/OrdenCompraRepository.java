package com.example.demo.repository;

import com.example.demo.model.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {

    List<OrdenCompra> findByUsuarioId(Long usuarioId);

    List<OrdenCompra> findByUsuarioDireccionId(Long usuarioDireccionId);

    List<OrdenCompra> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);

    @Query("SELECT o FROM OrdenCompra o WHERE o.total >= :minTotal AND o.total <= :maxTotal")
    List<OrdenCompra> findByTotalBetween(Float minTotal, Float maxTotal);

    @Query("SELECT SUM(o.total) FROM OrdenCompra o WHERE o.usuario.id = :usuarioId")
    Float sumTotalByUsuarioId(Long usuarioId);

    @Query("SELECT COUNT(o) FROM OrdenCompra o WHERE o.usuario.id = :usuarioId")
    Long countByUsuarioId(Long usuarioId);
}