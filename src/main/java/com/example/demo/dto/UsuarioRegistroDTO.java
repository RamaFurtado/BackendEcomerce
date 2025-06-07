package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioRegistroDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Email(message = "Email inválido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @NotBlank(message = "El DNI es obligatorio")
    private String dni;

    @NotBlank(message = "El rol es obligatorio") // NUEVO
    private String rol; // debe ser "ADMIN", "USUARIO", etc. según tu enum Rol


    // Si querés incluir direccion, agregás aquí un DTO para direccion (opcional)
    // private DireccionDTO direccion;


}