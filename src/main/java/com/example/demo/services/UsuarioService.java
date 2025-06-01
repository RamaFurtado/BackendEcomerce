package com.example.demo.services;

import com.example.demo.dto.CambiarRolDTO;
import com.example.demo.dto.UsuarioRegistroDTO;
import com.example.demo.dto.UsuarioResponseDTO;
import com.example.demo.enums.Rol;
import com.example.demo.model.Descuento;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario guardarUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }



    public UsuarioResponseDTO registrarUsuario(UsuarioRegistroDTO dto) {
        if (existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        Usuario usuario = mapRegistroDtoToEntity(dto);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // encriptar

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return mapEntityToResponseDto(usuarioGuardado);
    }


    public Usuario mapRegistroDtoToEntity(UsuarioRegistroDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());

        usuario.setPassword(dto.getPassword());
        usuario.setDni(dto.getDni());
        usuario.setRol(Rol.USUARIO); // rol por defecto
        usuario.setActivo(true); // activo por defecto
        usuario.setDirecciones(new ArrayList<>());
        usuario.setOrdenes(new ArrayList<>());
        return usuario;
    }

    public UsuarioResponseDTO mapEntityToResponseDto(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setDni(usuario.getDni());
        dto.setRol(usuario.getRol().name());
        return dto;
    }

    public void cambiarRolUsuario(CambiarRolDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setRol(dto.getNuevoRol());
        usuarioRepository.save(usuario);
    }


}

