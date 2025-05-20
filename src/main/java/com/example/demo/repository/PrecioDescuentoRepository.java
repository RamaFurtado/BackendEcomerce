package com.example.demo.repository;

import com.example.demo.model.PrecioDescuento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PrecioDescuentoRepository extends JpaRepository<PrecioDescuento, Long> {

    List<PrecioDescuento> findByDescuentoId(Long descuentoId);

    List<PrecioDescuento> findByPrecioId(Long precioId);

    /**
     * Busca relaciones precio-descuento con descuentos activos en la fecha dada
     */
    @Query("SELECT pd FROM PrecioDescuento pd JOIN pd.descuento d WHERE d.fechaInicio <= :fecha AND d.fechaFinal >= :fecha")
    List<PrecioDescuento> findActiveDiscountsAtDate(Date fecha);

    /**
     * Elimina relaciones por descuento ID
     */
    void deleteByDescuentoId(Long descuentoId);

    /**
     * Elimina relaciones por precio ID
     */
    void deleteByPrecioId(Long precioId);

    /**
     * Verifica si existe una relación entre un precio y un descuento
     */
    boolean existsByPrecioIdAndDescuentoId(Long precioId, Long descuentoId);


    void deleteByPrecioIdAndDescuentoId(Long precioId, Long descuentoId);

}