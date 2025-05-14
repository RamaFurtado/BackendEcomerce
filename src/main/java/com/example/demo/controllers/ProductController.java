package com.example.demo.controllers;

import com.example.demo.models.Dto.ProductCreateDTO;
import com.example.demo.models.Entities.Product;
import com.example.demo.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductCreateDTO productCreateDTO) {
        Product product = service.createProduct(productCreateDTO);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }


}
