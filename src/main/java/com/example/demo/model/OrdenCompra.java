package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenCompra extends BaseEntity {

    private LocalDate fecha;
    private int total;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "ordenCompra", cascade = CascadeType.ALL)
    private List<OrdenCompraDetalle> detalles;
}
