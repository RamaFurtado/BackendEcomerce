package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String calle;
    private String ciudad;
    private String provincia;
    private String pais;
    private String codigoPostal;
    private String localidad;
    private int usuarioId;

    @OneToMany(mappedBy = "direccion")
    private List<UsuarioDireccion> usuarios;
}
