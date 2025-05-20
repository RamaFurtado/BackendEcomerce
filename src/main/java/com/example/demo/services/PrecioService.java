package com.example.demo.services;

import com.example.demo.model.Precio;
import com.example.demo.repository.PrecioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrecioService {
    private final PrecioRepository precioRepository;

    public List<Precio> buscarConDescuentosActivos() {
        return precioRepository.findWithActiveDiscounts();
    }

    public List<Precio> buscarPorMargenMinimo(Double margen) {
        return precioRepository.findByMargenGananciaMinimo(margen);
    }




}