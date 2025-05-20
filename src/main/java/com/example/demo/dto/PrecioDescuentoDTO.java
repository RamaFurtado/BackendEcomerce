package com.example.demo.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrecioDescuentoDTO {

    @NotNull
    private Long precioId;

    @NotNull
    private Long descuentoId;
}

