package com.example.demo.dto;


import com.example.demo.enums.Rol;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CambiarRolDTO {

    @NotNull
    private Long id;

    @NotNull
    private Rol nuevoRol;
}
