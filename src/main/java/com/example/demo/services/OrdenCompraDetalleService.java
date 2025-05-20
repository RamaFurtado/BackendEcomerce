package com.example.demo.services;

import com.example.demo.model.OrdenCompraDetalle;
import com.example.demo.repository.OrdenCompraDetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdenCompraDetalleService {

    @Autowired
    private OrdenCompraDetalleRepository ordenCompraDetalleRepository;

    public List<OrdenCompraDetalle> obtenerPorOrden(Long ordenId) {
        return ordenCompraDetalleRepository.findByOrdenCompraId(ordenId);
    }

    public OrdenCompraDetalle crear(OrdenCompraDetalle detalle) {
        return ordenCompraDetalleRepository.save(detalle);
    }


    public void eliminarPorOrden(Long ordenId) {
        ordenCompraDetalleRepository.deleteByOrdenCompraId(ordenId);
    }
}