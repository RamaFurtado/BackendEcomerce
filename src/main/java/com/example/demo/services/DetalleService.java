package com.example.demo.services;


import com.example.demo.model.Detalle;
import com.example.demo.repository.DetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class DetalleService {

    @Autowired
    private DetalleRepository detalleRepository;

    public List<Detalle> obtenerPorProducto(Long productoId) {
        return detalleRepository.findByProductoId(productoId);
    }



    public Detalle crearDetalle(Detalle detalle) {
        return detalleRepository.save(detalle);
    }

    public void eliminar(Long id) {
        detalleRepository.deleteById(id);
    }
}