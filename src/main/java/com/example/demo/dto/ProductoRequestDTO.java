package com.example.demo.dto;

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
    private String categoriaNombre;

}
