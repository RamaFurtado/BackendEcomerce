package com.example.demo.services;

import com.example.demo.models.Entities.Product;
import com.example.demo.models.Dto.ProductCreateDTO;
import com.example.demo.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }


    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    // Método para crear un nuevo producto usando el DTO
    public Product createProduct(ProductCreateDTO productCreateDTO) {
        Product product = new Product();


        product.setName(productCreateDTO.getName());
        product.setDescription(productCreateDTO.getDescription());
        product.setPrice(productCreateDTO.getPrice());
        product.setImageUrl(productCreateDTO.getImageUrl());
        product.setCategory(productCreateDTO.getCategory());
        product.setGenero(productCreateDTO.getGenero());
        product.setTalle(productCreateDTO.getTalle());
        product.setStock(productCreateDTO.getStock());
        product.setModelo(productCreateDTO.getModelo());

        // Guardar el producto en la base de datos
        return repository.save(product);
    }
}
