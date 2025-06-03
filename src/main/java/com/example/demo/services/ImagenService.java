package com.example.demo.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.model.Imagen;
import com.example.demo.repository.ImagenRepository;
import com.example.demo.services.generics.GenericServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImagenService extends GenericServiceImpl<Imagen, Long> {

    private final ImagenRepository imagenRepository;
    private final Cloudinary cloudinary;

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

    public Imagen subirYGuardarImagen(MultipartFile archivo) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(archivo.getBytes(), ObjectUtils.emptyMap());
        String url = uploadResult.get("secure_url").toString();

        if (imagenRepository.existsByUrl(url)) {
            throw new RuntimeException("La imagen ya existe");
        }

        Imagen imagen = new Imagen();
        imagen.setUrl(url);
        return imagenRepository.save(imagen);
    }
}