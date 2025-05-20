package com.example.demo.services;

import com.example.demo.model.OrdenCompra;
import com.example.demo.repository.OrdenCompraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdenCompraService {
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