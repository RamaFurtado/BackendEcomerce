package com.example.demo.services;

import com.example.demo.model.Talle;
import com.example.demo.repository.TalleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TalleService {

    private final TalleRepository talleRepository;

    public TalleService(TalleRepository talleRepository) {
        this.talleRepository = talleRepository;
    }

    public Optional<Talle> buscarPorNumero(String numero) {
        return talleRepository.findByNumero(numero);
    }

    public boolean existePorNumero(String numero) {
        return talleRepository.existsByNumero(numero);
    }

    public List<Talle> buscarPorNumeroParcial(String parcial) {
        return talleRepository.findByNumeroContaining(parcial);
    }

    public List<Talle> obtenerTodos() {
        return talleRepository.findAll();
    }

    public Talle obtenerPorId(Long id) {
        return talleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Talle no encontrado"));
    }

    public Talle crear(Talle talle) {
        return talleRepository.save(talle);
    }

    public void eliminar(Long id) {
        Talle talle = talleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Talle no encontrado"));
        talleRepository.delete(talle);
    }
}