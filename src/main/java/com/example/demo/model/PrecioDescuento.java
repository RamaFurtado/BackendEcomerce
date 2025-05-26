package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrecioDescuento extends BaseEntity {

    private double precioFinal;

    @ManyToOne
    @JoinColumn(name = "detalle_id")
    private Detalle detalle;

    @ManyToOne
    @JoinColumn(name = "descuento_id")
    private Descuento descuento;

    @ManyToOne
    @JoinColumn(name = "precio_id")
    private Precio precio;
}
