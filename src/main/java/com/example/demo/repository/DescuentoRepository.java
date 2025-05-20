package com.example.demo.repository;

import com.example.demo.model.Descuento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface DescuentoRepository extends JpaRepository<Descuento, Long> {

    List<Descuento> findByFechaInicioBefore(Date fecha);

    List<Descuento> findByFechaFinalAfter(Date fecha);

    List<Descuento> findByFechaInicioBetween(Date fechaInicio, Date fechaFin);

    List<Descuento> findByFechaFinalBetween(Date fechaInicio, Date fechaFin);

    //Busca descuentos activos en una fecha específica

    List<Descuento> findByFechaInicioBeforeAndFechaFinalAfter(Date fecha, Date mismaFecha);

    //Busca por porcentaje de descuento

    List<Descuento> findByPorcentaje(Double porcentaje);
}