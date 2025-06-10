package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioUpdateDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El DNI no puede estar vacío")
    @Size(min = 7, max = 8, message = "El DNI debe tener entre 7 y 8 dígitos")
    private String dni;
}