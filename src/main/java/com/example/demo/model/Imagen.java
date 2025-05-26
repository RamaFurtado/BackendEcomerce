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
public class Imagen extends BaseEntity {

    private String url;

    @OneToMany(mappedBy = "imagen")
    @JsonIgnore
    private List<DetalleImagen> detalles;
}
