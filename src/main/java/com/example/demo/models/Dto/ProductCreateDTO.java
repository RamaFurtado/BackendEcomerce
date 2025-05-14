package com.example.demo.models.Dto;

import com.example.demo.models.Enums.ProductCategory;
import com.example.demo.models.Enums.ProductGender;
import com.example.demo.models.Enums.ProductSize;
import jakarta.validation.constraints.*;

public class ProductCreateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    @Positive(message = "El precio debe ser mayor a 0")
    private double price;

    @NotBlank(message = "La URL de la imagen es obligatoria")
    private String imageUrl;

    @NotNull(message = "La categoría es obligatoria")
    private ProductCategory category;

    @NotNull(message = "El género es obligatorio")
    private ProductGender genero;

    @NotNull(message = "El talle es obligatorio")
    private ProductSize talle;

    @Positive(message = "El stock debe ser mayor a 0")
    private int stock;

    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public ProductGender getGenero() {
        return genero;
    }

    public void setGenero(ProductGender genero) {
        this.genero = genero;
    }

    public ProductSize getTalle() {
        return talle;
    }

    public void setTalle(ProductSize talle) {
        this.talle = talle;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
