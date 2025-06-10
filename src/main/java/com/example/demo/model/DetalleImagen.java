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
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "detalle_id")
    @JsonIgnore
    private Detalle detalle;

    @ManyToOne
    @JoinColumn(name = "imagen_id")
    @JsonIgnore
    private Imagen imagen;

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public Detalle getDetalle() {
        return detalle;
    }

    public void setDetalle(Detalle detalle) {
        this.detalle = detalle;
    }
}
