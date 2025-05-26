package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDireccion extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference(value = "usuario-ud")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "direccion_id")
    @JsonBackReference(value = "direccion-ud")
    private Direccion direccion;
}
