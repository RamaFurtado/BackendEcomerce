package com.example.demo.repository;


import com.example.demo.model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {

    List<Direccion> findByProvincia(String provincia);

    List<Direccion> findByPais(String pais);

    List<Direccion> findByLocalidad(String localidad);

    List<Direccion> findByPaisAndProvinciaAndLocalidad(String pais, String provincia, String localidad);

    List<Direccion> findByUsuarioId(Long usuarioId);
}