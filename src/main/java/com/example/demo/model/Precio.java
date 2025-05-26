package com.example.demo.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Precio extends BaseEntity {

    private Double precioCompra;
    private Double precioVenta;

    @OneToMany(mappedBy = "precio")
    @JsonIgnore
    private List<Detalle> detalles;

    @OneToMany(mappedBy = "precio")
    private Set<PrecioDescuento> preciosDescuento;
}
