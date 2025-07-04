package com.example.demo.services;

import com.example.demo.dto.PrecioDescuentoDTO;
import com.example.demo.model.Descuento;
import com.example.demo.model.Precio;
import com.example.demo.model.PrecioDescuento;
import com.example.demo.repository.DescuentoRepository;
import com.example.demo.repository.PrecioDescuentoRepository;
import com.example.demo.repository.PrecioRepository;
import com.example.demo.services.generics.GenericServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrecioDescuentoService extends GenericServiceImpl<PrecioDescuento, Long> {

    private final PrecioRepository precioRepository;
    private final DescuentoRepository descuentoRepository;

    @Autowired
    public PrecioDescuentoService(PrecioDescuentoRepository repository,
                                  PrecioRepository precioRepository,
                                  DescuentoRepository descuentoRepository) {
        super(repository);
        this.precioRepository = precioRepository;
        this.descuentoRepository = descuentoRepository;
    }

    public List<PrecioDescuento> obtenerDescuentosActivos(Date fecha) {
        return ((PrecioDescuentoRepository) repository).findActiveDiscountsAtDate(fecha);
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
        ((PrecioDescuentoRepository) repository).deleteByPrecioIdAndDescuentoId(precioId, descuentoId);
    }

    public void eliminarPorPrecio(Long id) {
        ((PrecioDescuentoRepository) repository).deleteByPrecioId(id);
    }
}
