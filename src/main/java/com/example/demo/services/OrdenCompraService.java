package com.example.demo.services;

import com.example.demo.model.OrdenCompra;
import com.example.demo.repository.OrdenCompraRepository;
import com.example.demo.services.generics.GenericServiceImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrdenCompraService extends GenericServiceImpl<OrdenCompra, Long> {

    private final OrdenCompraRepository ordenCompraRepository;

    public List<OrdenCompra> obtenerPorUsuario(Long usuarioId) {
        return ordenCompraRepository.findByUsuarioId(usuarioId);
    }

    public List<OrdenCompra> obtenerPorDireccion(Long direccionId) {
        return ordenCompraRepository.findByUsuarioDireccionId(direccionId);
    }

    public List<OrdenCompra> obtenerPorRangoFecha(LocalDate inicio, LocalDate fin) {
        return ordenCompraRepository.findByFechaBetween(inicio, fin);
    }

    public List<OrdenCompra> obtenerPorRangoTotal(Float min, Float max) {
        return ordenCompraRepository.findByTotalBetween(min, max);
    }

    public Float totalGastadoPorUsuario(Long usuarioId) {
        return ordenCompraRepository.sumTotalByUsuarioId(usuarioId);
    }

    public Long cantidadComprasPorUsuario(Long usuarioId) {
        return ordenCompraRepository.countByUsuarioId(usuarioId);
    }
}
