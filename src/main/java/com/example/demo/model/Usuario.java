package com.example.demo.model;

import com.example.demo.enums.Rol;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends BaseEntity {

    private String nombre;
    private String email;
    private String password;
    private String dni;
    private int direccionId;


    @Enumerated(EnumType.STRING)
    private Rol rol;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "usuario-ud")
    private List<UsuarioDireccion> direcciones;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<OrdenCompra> ordenes;
}
