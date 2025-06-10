package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DetalleRequestDTO {
    @NotBlank
    private String talle;

    @NotNull
    @Min(0)
    private Integer stock;

    private Boolean estado = true;

    @NotNull
    private Double precioCompra;

    @NotNull
    private Double precioVenta;

    private String color;
    private String marca;
    private List<String> imagenesUrls;

}
