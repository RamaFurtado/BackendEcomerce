package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Descuento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private double porcentaje;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFinal;

    @OneToMany(mappedBy = "descuento")
    private List<PrecioDescuento> precios;
}
