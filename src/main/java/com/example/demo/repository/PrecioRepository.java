package com.example.demo.repository;
import com.example.demo.model.Precio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrecioRepository extends JpaRepository<Precio, Long> {

    List<Precio> findByPrecioCompraLessThan(Double precioCompra);

    List<Precio> findByPrecioVentaLessThan(Double precioVenta);

    List<Precio> findByPrecioCompraGreaterThan(Double precioCompra);

    List<Precio> findByPrecioVentaGreaterThan(Double precioVenta);

    List<Precio> findByPrecioCompraBetween(Double minPrecio, Double maxPrecio);

    List<Precio> findByPrecioVentaBetween(Double minPrecio, Double maxPrecio);

    /**
     * Calcula el margen de ganancia (precio venta - precio compra)
     */
    @Query("SELECT p FROM Precio p WHERE (p.precioVenta - p.precioCompra) >= :margenMinimo")
    List<Precio> findByMargenGananciaMinimo(Double margenMinimo);

    /**
     * Busca precios con descuentos activos
     */
    @Query("SELECT DISTINCT p FROM Precio p JOIN p.preciosDescuento pd JOIN pd.descuento d WHERE d.fechaInicio <= CURRENT_DATE AND d.fechaFinal >= CURRENT_DATE")
    List<Precio> findWithActiveDiscounts();
}