package com.example.demo.services;

import com.example.demo.model.Descuento;
import com.example.demo.repository.DescuentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DescuentoService {

    @Autowired
    private DescuentoRepository descuentoRepository;

    public List<Descuento> obtenerActivos() {
        Date hoy = new Date();
        return descuentoRepository.findByFechaInicioBeforeAndFechaFinalAfter(hoy, hoy);
    }

    public List<Descuento> obtenerEntreFechas(Date inicio, Date fin) {
        return descuentoRepository.findByFechaInicioBetween(inicio, fin);
    }

    public Descuento crear(Descuento descuento) {
        return descuentoRepository.save(descuento);
    }

    public void eliminar(Long id) {
        descuentoRepository.deleteById(id);
    }
}
