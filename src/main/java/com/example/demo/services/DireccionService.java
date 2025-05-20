package com.example.demo.services;

import com.example.demo.model.Direccion;
import com.example.demo.repository.DireccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DireccionService {

    private final DireccionRepository direccionRepository;

    public List<Direccion> obtenerPorUsuario(Long usuarioId) {
        return direccionRepository.findByUsuarioId(usuarioId);
    }

    public Direccion crear(Direccion direccion) {
        return direccionRepository.save(direccion);
    }
}
