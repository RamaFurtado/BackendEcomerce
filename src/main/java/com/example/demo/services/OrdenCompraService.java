package com.example.demo.services;

import com.example.demo.dto.CrearOrdenRequest; // Mantener si lo usas en otros lugares
import com.example.demo.dto.ProductoPagoDTO; // Necesitamos esta clase para el nuevo método de creación de orden
import com.example.demo.model.*;
import com.example.demo.repository.DetalleRepository; // Necesitamos este repositorio para buscar Detalles
import com.example.demo.repository.OrdenCompraRepository;
import com.example.demo.repository.UsuarioRepository; // Necesitamos este repositorio para buscar Usuarios
import com.example.demo.services.generics.GenericServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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


    @Transactional
    public OrdenCompra guardarOrdenCompra(OrdenCompra ordenCompra) {
        System.out.println("-> [OrdenCompraService] Iniciando transacción para guardar Orden de Compra.");


        if (ordenCompra.getDetalles() != null) {
            ordenCompra.getDetalles().forEach(detalleOrden -> {

                detalleOrden.setOrdenCompra(ordenCompra);

            });
        }
        OrdenCompra savedOrden = ordenCompraRepository.save(ordenCompra);
        System.out.println("-> [OrdenCompraService] Orden de Compra guardada con ID: " + savedOrden.getId());
        return savedOrden;
    }

}
