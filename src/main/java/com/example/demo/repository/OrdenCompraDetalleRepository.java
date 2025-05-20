package com.example.demo.repository;

import com.example.demo.model.OrdenCompraDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenCompraDetalleRepository extends JpaRepository<OrdenCompraDetalle, Long> {

    List<OrdenCompraDetalle> findByOrdenCompraId(Long ordenCompraId);

    List<OrdenCompraDetalle> findByDetalleId(Long detalleId);

    @Query("SELECT ocd FROM OrdenCompraDetalle ocd JOIN ocd.ordenCompra oc WHERE oc.usuario.id = :usuarioId")
    List<OrdenCompraDetalle> findByUsuarioId(Long usuarioId);

    @Query("SELECT COUNT(ocd) FROM OrdenCompraDetalle ocd WHERE ocd.ordenCompra.id = :ordenCompraId")
    Long countByOrdenCompraId(Long ordenCompraId);

    void deleteByOrdenCompraId(Long ordenCompraId);
}