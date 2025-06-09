package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenCompra extends BaseEntity {

    private LocalDate fecha;
    private float total;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private String estado;


    @OneToMany(mappedBy = "ordenCompra", cascade = CascadeType.ALL)
    private List<OrdenCompraDetalle> detalles = new ArrayList<>();

}
