package com.example.demo.services;

import com.example.demo.model.Precio;
import com.example.demo.repository.PrecioRepository;
import com.example.demo.services.generics.GenericServiceImpl;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrecioService extends GenericServiceImpl<Precio, Long> {

    private final PrecioRepository precioRepository;

    public PrecioService(PrecioRepository precioRepository) {
        super(precioRepository);
        this.precioRepository = precioRepository;
    }

    public List<Precio> buscarPorMargenMinimo(Double margen) {
        return precioRepository.findByMargenGananciaMinimo(margen);
    }

    public List<Precio> buscarConDescuentosActivos() {
        return precioRepository.findWithActiveDiscounts();
    }
}
