package com.example.demo.services;

import com.example.demo.dto.PrecioDescuentoDTO;
import com.example.demo.model.Descuento;
import com.example.demo.model.Precio;
import com.example.demo.model.PrecioDescuento;
import com.example.demo.repository.DescuentoRepository;
import com.example.demo.repository.PrecioDescuentoRepository;
import com.example.demo.repository.PrecioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrecioDescuentoService {
    private final PrecioDescuentoRepository repository;
    private final PrecioRepository precioRepository;
    private final DescuentoRepository descuentoRepository;

    public List<PrecioDescuento> obtenerDescuentosActivos(Date fecha) {
        return repository.findActiveDiscountsAtDate(fecha);
    }

    public PrecioDescuento crearRelacion(PrecioDescuentoDTO dto) {
        Precio precio = precioRepository.findById(dto.getPrecioId())
                .orElseThrow(() -> new RuntimeException("Precio no encontrado"));
        Descuento descuento = descuentoRepository.findById(dto.getDescuentoId())
                .orElseThrow(() -> new RuntimeException("Descuento no encontrado"));

        PrecioDescuento relacion = new PrecioDescuento();
        relacion.setPrecio(precio);
        relacion.setDescuento(descuento);
        return repository.save(relacion);
    }
    public void eliminarRelacion(Long precioId, Long descuentoId) {
        repository.deleteByPrecioIdAndDescuentoId(precioId, descuentoId);
    }
    public void eliminarPorPrecio(Long id) {
        repository.deleteByPrecioId(id);
    }


}
