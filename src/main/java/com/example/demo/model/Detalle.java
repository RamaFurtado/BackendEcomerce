package com.example.demo.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Detalle extends BaseEntity {

    private Boolean estado;
    private String color;
    private String marca;
    private int stock;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    @JsonIgnore
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "precio_id")
    private Precio precio;

    @ManyToOne
    @JoinColumn(name = "talle_id")
    private Talles talle;

    @OneToMany(mappedBy = "detalle", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DetalleImagen> imagenes;
}
