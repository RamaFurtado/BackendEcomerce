package com.example.demo.model;

import com.example.demo.enums.Sexo;
import com.example.demo.enums.TipoProducto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto extends BaseEntity {

    private String nombre;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    private TipoProducto tipoProducto;

    @ManyToOne
    @JoinColumn(name="categoria_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<Detalle> detalles;
}
