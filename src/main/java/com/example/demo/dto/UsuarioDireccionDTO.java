package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDireccionDTO {

    @NotNull
    private Long usuarioId;

    @NotNull
    private Long direccionId;
}