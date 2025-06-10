package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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


    private Double subtotal;


    @ManyToOne
    @JoinColumn(name = "orden_id")
    @JsonIgnore
    private OrdenCompra ordenCompra;

    @ManyToOne
    @JoinColumn(name = "detalle_id")
    private Detalle detalle;
}