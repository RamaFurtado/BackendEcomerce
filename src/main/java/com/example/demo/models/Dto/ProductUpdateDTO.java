package com.example.demo.models.Dto;

import com.example.demo.models.Enums.ProductCategory;
import com.example.demo.models.Enums.ProductGender;
import com.example.demo.models.Enums.ProductSize;
import jakarta.validation.constraints.*;

public class ProductUpdateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    @Positive(message = "El precio debe ser mayor a 0")
    private double price;

    private String imageUrl;

    private ProductCategory category;

    private ProductGender genero;

    private ProductSize talle;

    @Positive(message = "El stock debe ser mayor a 0")
    private int stock;

    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;

    // Getters and Setters
    // (Similares a los de ProductCreateDTO)
}
