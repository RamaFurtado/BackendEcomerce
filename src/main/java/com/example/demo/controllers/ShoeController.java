package com.example.demo.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shoes")
public class ShoeController implements BaseController<Shoe> {

    private final ShoeServiceImpl service;

    public ShoeController(ShoeServiceImpl service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public List<Shoe> findAll() {
        return service.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public Shoe findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Override
    @PostMapping
    public Shoe save(@RequestBody Shoe shoe) {
        return service.save(shoe);
    }

    @Override
    @PutMapping("/{id}")
    public Shoe update(@PathVariable Long id, @RequestBody Shoe shoe) {
        return service.update(id, shoe);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
