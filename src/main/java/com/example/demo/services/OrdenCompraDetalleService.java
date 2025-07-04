package com.example.demo.services;

import com.example.demo.model.OrdenCompraDetalle;
import com.example.demo.repository.OrdenCompraDetalleRepository;
import com.example.demo.services.generics.GenericServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdenCompraDetalleService extends GenericServiceImpl<OrdenCompraDetalle, Long> {

    private final OrdenCompraDetalleRepository ordenCompraDetalleRepository;

    public List<OrdenCompraDetalle> obtenerPorOrden(Long ordenId) {
        return ordenCompraDetalleRepository.findByOrdenCompraId(ordenId);
    }

    @Override
    public OrdenCompraDetalle crear(OrdenCompraDetalle detalle) {

        return ordenCompraDetalleRepository.save(detalle);
    }

    public void eliminarPorOrden(Long ordenId) {
        ordenCompraDetalleRepository.deleteByOrdenCompraId(ordenId);
    }
}
