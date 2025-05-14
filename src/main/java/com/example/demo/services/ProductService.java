package com.example.demo.services;

import com.example.demo.models.Entities.Product;
import org.springframework.stereotype.Service;
import com.example.demo.repositories.ProductRepository;

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

    public Product saveProduct(Product product) {
        return repository.save(product);
    }
}