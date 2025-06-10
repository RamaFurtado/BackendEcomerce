package com.example.demo.dto;

import com.example.demo.enums.TipoProducto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoCatalogoDTO {
    private Long id;
    private String nombre;
    private String marca;
    private String color;
    private Double precio;
    private String categoria;
    private String imagenUrl;
    private TipoProducto tipoProducto;
}