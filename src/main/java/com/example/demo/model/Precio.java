package com.example.demo.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
public class Precio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double precioCompra;
    private Double precioVenta;


    @OneToMany(mappedBy = "precio")
    @JsonIgnore
    private List<Detalle> detalles;
    @OneToMany(mappedBy = "precio")
    private Set<PrecioDescuento> preciosDescuento;
}
