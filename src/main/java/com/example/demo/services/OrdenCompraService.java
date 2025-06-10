package com.example.demo.services;

import com.example.demo.dto.CrearOrdenRequest;
import com.example.demo.dto.ProductoPagoDTO;
import com.example.demo.model.*;
import com.example.demo.repository.OrdenCompraRepository;
import com.example.demo.services.generics.GenericServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.ArrayList;
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


    @Transactional
    public OrdenCompra crearOrden(CrearOrdenRequest request) {
        System.out.println("Entró a crearOrden");
        Usuario usuario = new Usuario();
        usuario.setId(request.getUsuarioId());

        OrdenCompra orden = new OrdenCompra();
        orden.setUsuario(usuario);
        orden.setFecha(LocalDate.now());

        float total = 0f;
        List<OrdenCompraDetalle> detalles = new ArrayList<>();

        for (ProductoPagoDTO prod : request.getProductos()) {
            Detalle detalle = new Detalle();
            detalle.setId(prod.getDetalleId());

            OrdenCompraDetalle ordenDetalle = new OrdenCompraDetalle();
            ordenDetalle.setCantidad(prod.getCantidad());
            ordenDetalle.setDetalle(detalle);
            ordenDetalle.setOrdenCompra(orden); // asignación muy importante

            detalles.add(ordenDetalle);
        }

        orden.setDetalles(detalles);


        orden.setTotal(total);

        return ordenCompraRepository.save(orden);
    }




    @Transactional
    public void actualizarEstadoPago(Long ordenId, String nuevoEstado) {
        OrdenCompra orden = ordenCompraRepository.findById(ordenId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        if (nuevoEstado.equalsIgnoreCase("approved")) {
            orden.setEstado("APROBADA");
        } else {
            orden.setEstado("FALLIDA");
        }

        ordenCompraRepository.save(orden);
    }


}
