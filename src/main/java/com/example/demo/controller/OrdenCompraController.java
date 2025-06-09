package com.example.demo.controller;
import com.example.demo.controller.generics.GenericController;
import com.example.demo.dto.CrearOrdenRequest;
import com.example.demo.model.OrdenCompra;
import com.example.demo.services.OrdenCompraService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenCompraController extends GenericController<OrdenCompra, Long> {

    private final OrdenCompraService ordenCompraService;

    public OrdenCompraController(OrdenCompraService ordenCompraService) {
        super(ordenCompraService);
        this.ordenCompraService = ordenCompraService;
    }

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
    @PostMapping("/crear")
    public ResponseEntity<OrdenCompra> crearOrden(@RequestBody CrearOrdenRequest request) {
        OrdenCompra nuevaOrden = ordenCompraService.crearOrden(request);
        return ResponseEntity.ok(nuevaOrden);
    }


}
