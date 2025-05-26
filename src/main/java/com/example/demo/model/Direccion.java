package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Direccion extends BaseEntity {

    private String calle;
    private String ciudad;
    private String provincia;
    private String pais;
    private String codigoPostal;
    private String localidad;
    private int usuarioId;

    @OneToMany(mappedBy = "direccion")
    @JsonManagedReference(value = "direccion-ud")
    private List<UsuarioDireccion> usuarios;
}
