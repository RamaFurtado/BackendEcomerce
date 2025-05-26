package com.example.demo.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleImagen extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "detalle_id")
    @JsonIgnore
    private Detalle detalle;

    @ManyToOne
    @JoinColumn(name = "imagen_id")
    @JsonIgnore
    private Imagen imagen;
}
