package com.example.demo.services;


import com.example.demo.model.Detalle;
import com.example.demo.repository.DetalleRepository;
import com.example.demo.services.generics.GenericServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class DetalleService extends GenericServiceImpl<Detalle, Long> {

    private final DetalleRepository detalleRepository;

    public List<Detalle> obtenerPorProducto(Long productoId) {
        return detalleRepository.findByProductoId(productoId);
    }

    public Detalle crearDetalle(Detalle detalle) {
        return detalleRepository.save(detalle);
    }


}