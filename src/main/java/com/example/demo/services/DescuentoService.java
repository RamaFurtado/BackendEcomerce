package com.example.demo.services;

import com.example.demo.model.Descuento;
import com.example.demo.repository.DescuentoRepository;
import com.example.demo.services.generics.GenericServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DescuentoService extends GenericServiceImpl<Descuento, Long> {

    private final DescuentoRepository descuentoRepository;

    public List<Descuento> obtenerActivos() {
        Date hoy = new Date();
        return descuentoRepository.findByFechaInicioBeforeAndFechaFinalAfter(hoy, hoy);
    }

}