package com.example.demo.services;

import com.example.demo.model.Direccion;
import com.example.demo.model.UsuarioDireccion;

import com.example.demo.repository.DireccionRepository;
import com.example.demo.repository.UsuarioDireccionRepository;
import com.example.demo.services.generics.IGenericService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DireccionService implements IGenericService<Direccion, Long> {

    private final DireccionRepository direccionRepository;
    private final UsuarioDireccionRepository usuarioDireccionRepository;

    // --- Implementación de los métodos de IGenericService ---

    @Override
    public Direccion crear(Direccion entity) {
        return direccionRepository.save(entity);
    }

    @Override
    public Direccion obtenerPorId(Long id) {
        return direccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada con ID: " + id));
    }

    @Override
    public List<Direccion> obtenerTodos() {
        return direccionRepository.findAll();
    }

    @Override
    public Direccion actualizar(Long id, Direccion entity) {
        // Primero, busca si la dirección existe
        Optional<Direccion> existingDireccionOptional = direccionRepository.findById(id);

        if (existingDireccionOptional.isEmpty()) {
            throw new RuntimeException("Dirección no encontrada con ID: " + id);
        }

        Direccion existingDireccion = existingDireccionOptional.get();

        // Actualiza solo los campos permitidos desde la 'entity'
        // ¡Importante!: Asegúrate de que 'entity' contenga los datos actualizados.
        existingDireccion.setCalle(entity.getCalle());
        existingDireccion.setCiudad(entity.getCiudad());
        existingDireccion.setProvincia(entity.getProvincia());
        existingDireccion.setPais(entity.getPais());
        existingDireccion.setCodigoPostal(entity.getCodigoPostal());
        existingDireccion.setLocalidad(entity.getLocalidad());
        // No actualices el ID directamente, ya que estamos actualizando una entidad existente.

        return direccionRepository.save(existingDireccion);
    }

    @Override
    public void eliminar(Long id) {
        if (!direccionRepository.existsById(id)) {
            throw new RuntimeException("Dirección no encontrada con ID: " + id);
        }
        direccionRepository.deleteById(id);
    }

    // --- Métodos específicos de DireccionService ---

    public List<Direccion> obtenerPorUsuario(Long userId) {
        // 1. Encuentra todas las relaciones UsuarioDireccion para este usuario
        List<UsuarioDireccion> usuarioDirecciones = usuarioDireccionRepository.findByUsuario_Id(userId);

        // 2. Extrae las entidades Direccion de esas relaciones
        return usuarioDirecciones.stream()
                .map(UsuarioDireccion::getDireccion)
                .collect(Collectors.toList());
    }
}