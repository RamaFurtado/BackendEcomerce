package com.example.demo.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Detalle {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private List<DetalleImagen> imagenes;
}
