package com.example.demo.services;

import com.example.demo.model.Imagen;
import com.example.demo.repository.ImagenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImagenService {

    @Autowired
    private ImagenRepository imagenRepository;

    public Imagen crear(Imagen imagen) {
        if (imagenRepository.existsByUrl(imagen.getUrl())) {
            throw new RuntimeException("La imagen ya existe");
        }
        return imagenRepository.save(imagen);
    }

    public Optional<Imagen> buscarPorUrl(String url) {
        return imagenRepository.findByUrl(url);
    }

    public List<Imagen> obtenerTodas() {
        return imagenRepository.findAll();
    }
}
