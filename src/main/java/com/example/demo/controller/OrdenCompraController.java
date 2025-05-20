package com.example.demo.controller;
import com.example.demo.model.OrdenCompra;
import com.example.demo.services.OrdenCompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
public class OrdenCompraController {

    private final OrdenCompraService ordenCompraService;

    @GetMapping("/usuario/{usuarioId}")
    public List<OrdenCompra> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return ordenCompraService.obtenerPorUsuario(usuarioId);
    }

    @GetMapping("/rango-fechas")
    public List<OrdenCompra> obtenerPorRangoFechas(@RequestParam LocalDate inicio, @RequestParam LocalDate fin) {
        return ordenCompraService.obtenerPorRangoFecha(inicio, fin);
    }

    @GetMapping("/total")
    public List<OrdenCompra> obtenerPorTotal(@RequestParam Float min, @RequestParam Float max) {
        return ordenCompraService.obtenerPorRangoTotal(min, max);
    }

    @GetMapping("/sum-total/{usuarioId}")
    public Float obtenerSumaTotal(@PathVariable Long usuarioId) {
        return ordenCompraService.totalGastadoPorUsuario(usuarioId);
    }

    @GetMapping("/count/{usuarioId}")
    public Long contarOrdenes(@PathVariable Long usuarioId) {
        return ordenCompraService.cantidadComprasPorUsuario(usuarioId);
    }
}
