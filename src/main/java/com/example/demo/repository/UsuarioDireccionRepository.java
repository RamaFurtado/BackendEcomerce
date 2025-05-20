package com.example.demo.repository;

import com.example.demo.model.UsuarioDireccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioDireccionRepository extends JpaRepository<UsuarioDireccion, Long> {

    List<UsuarioDireccion> findByUsuarioId(Long usuarioId);

    List<UsuarioDireccion> findByDireccionId(Long direccionId);

    UsuarioDireccion findByUsuarioIdAndDireccionId(Long usuarioId, Long direccionId);

    boolean existsByUsuarioIdAndDireccionId(Long usuarioId, Long direccionId);

    void deleteByUsuarioIdAndDireccionId(Long usuarioId, Long direccionId);
}