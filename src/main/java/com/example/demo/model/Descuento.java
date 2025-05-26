package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Descuento extends BaseEntity {

    private double porcentaje;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFinal;

    @OneToMany(mappedBy = "descuento")
    private List<PrecioDescuento> precios;
}
