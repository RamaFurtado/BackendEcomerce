package com.example.demo.services;

import com.example.demo.dto.UsuarioDireccionDTO;
import com.example.demo.model.Direccion;
import com.example.demo.model.Usuario;
import com.example.demo.model.UsuarioDireccion;
import com.example.demo.repository.DireccionRepository;
import com.example.demo.repository.UsuarioDireccionRepository;
import com.example.demo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioDireccionService {

    private final UsuarioDireccionRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final DireccionRepository direccionRepository;

    public UsuarioDireccion crearRelacion(UsuarioDireccionDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Direccion direccion = direccionRepository.findById(dto.getDireccionId())
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));

        UsuarioDireccion ud = new UsuarioDireccion();
        ud.setUsuario(usuario);
        ud.setDireccion(direccion);
        return repository.save(ud);
    }

    public void eliminarRelacion(Long usuarioId, Long direccionId) {
        repository.deleteByUsuarioIdAndDireccionId(usuarioId, direccionId);
    }

    public boolean existeRelacion(Long usuarioId, Long direccionId) {
        return repository.existsByUsuarioIdAndDireccionId(usuarioId, direccionId);
    }

    public List<UsuarioDireccion> obtenerPorUsuario(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    public List<UsuarioDireccion> obtenerPorDireccion(Long direccionId) {
        return repository.findByDireccionId(direccionId);
    }
}
