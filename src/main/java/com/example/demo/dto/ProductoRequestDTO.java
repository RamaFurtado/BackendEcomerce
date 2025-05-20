package com.example.demo.dto;

import com.example.demo.enums.Categoria;
import com.example.demo.enums.Sexo;
import com.example.demo.enums.TipoProducto;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductoRequestDTO {

    @NotBlank(message = "El nombre no debe estar vacío")
    private String nombre;

    @NotNull(message = "El sexo no debe ser nulo")
    private Sexo sexo;

    @NotNull(message = "El tipo de producto no debe ser nulo")
    private TipoProducto tipoProducto;

    @NotNull(message = "La categoría no debe ser nula")
    private Categoria categoria;

    @NotNull(message = "El talleId no debe ser nulo")
    private Long talleId;

    @NotBlank(message = "El color no debe estar vacío")
    private String color;

    @NotBlank(message = "La marca no debe estar vacía")
    private String marca;

    @NotNull(message = "El stock no debe ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    private Boolean estado = true;

    @NotNull(message = "El precio de compra no debe ser nulo")
    private Double precioCompra;

    @NotNull(message = "El precio de venta no debe ser nulo")
    private Double precioVenta;

    @NotEmpty(message = "Debe haber al menos una imagen")
    private List<String> imagenesUrls;
}
