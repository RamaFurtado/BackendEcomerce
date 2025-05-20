package com.example.demo.model;

import com.example.demo.enums.Categoria;
import com.example.demo.enums.Sexo;
import com.example.demo.enums.TipoProducto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;
    private TipoProducto tipoProducto;
    private Categoria categoria;


    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<Detalle> detalles;

}
