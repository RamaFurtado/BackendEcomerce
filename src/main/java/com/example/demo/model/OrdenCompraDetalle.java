package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenCompraDetalle extends BaseEntity {

    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "orden_id")
    private OrdenCompra ordenCompra;

    @ManyToOne
    @JoinColumn(name = "detalle_id")
    private Detalle detalle;
}
