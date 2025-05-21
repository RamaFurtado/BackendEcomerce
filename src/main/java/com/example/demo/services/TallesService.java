package com.example.demo.services;

import com.example.demo.model.Talles;
import com.example.demo.repository.TallesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TallesService {

    private final TallesRepository tallesRepository;

    public TallesService(TallesRepository tallesRepository) {
        this.tallesRepository = tallesRepository;
    }

    public Optional<Talles> buscarPorTalle(String talle) {
        return tallesRepository.findByTalle(talle);
    }



    public List<Talles> obtenerTodos() {
        return tallesRepository.findAll();
    }

    public Talles obtenerPorId(Long id) {
        return tallesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Talle no encontrado"));
    }

    public Talles crear(Talles talle) {
        return tallesRepository.save(talle);
    }

    public void eliminar(Long id) {
        Talles talle = tallesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Talle no encontrado"));
        tallesRepository.delete(talle);
    }
}